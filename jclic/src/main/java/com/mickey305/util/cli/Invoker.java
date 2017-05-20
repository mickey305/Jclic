package com.mickey305.util.cli;

import java.util.Collection;
import java.util.Stack;

import static com.mickey305.util.cli.TerminalCommand.RESULT_ERR;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class Invoker<C extends Command> implements BufferingInterface<C> {
    public static final boolean IGNORE = true;

    private Stack<C> commands;
    private Stack<C> trashCommands;
    private boolean errorIgnoreExecution;

    @FunctionalInterface
    public interface Callback {
        void onFinishEvent(int status);
    }

    public Stack<C> getCommands() {
        return commands;
    }

    public void setCommands(Stack<C> commands) {
        this.commands = commands;
    }

    private Stack<C> getTrashCommands() {
        return trashCommands;
    }

    private void setTrashCommands(Stack<C> trashCommands) {
        this.trashCommands = trashCommands;
    }

    public boolean isErrorIgnoreExecution() {
        return errorIgnoreExecution;
    }

    public void setErrorIgnoreExecution(boolean errorIgnoreExecution) {
        this.errorIgnoreExecution = errorIgnoreExecution;
    }

    public Invoker<C> errorIgnore(boolean errorIgnoreExecution) {
        this.setErrorIgnoreExecution(errorIgnoreExecution);
        return this;
    }

    public Invoker() {
        this.setCommands(new Stack<>());
        this.setTrashCommands(new Stack<>());
        this.errorIgnore(!IGNORE);
    }

    @Override
    public C add(C command) {
        if (!this.getTrashCommands().isEmpty())
            this.setTrashCommands(new Stack<>());
        return this.getCommands().push(command);
    }

    @Override
    public void addAll(Collection<C> commands) {
        commands.forEach(this::add);
    }

    @Override
    public C undo() {
        if (!this.getCommands().isEmpty()) {
            C popCommand = this.getCommands().pop();
            this.getTrashCommands().push(popCommand);
            return popCommand;
        } else {
            return null;
        }
    }

    @Override
    public C redo() {
        if (!this.getTrashCommands().isEmpty()) {
            C popCommand = this.getTrashCommands().pop();
            this.getCommands().push(popCommand);
            return popCommand;
        } else {
            return null;
        }
    }

    public void execute() {
        this.execute(null);
    }

    public void execute(Callback callback) {
        for (C command : this.getCommands()) {
            int status = command.execute();
            if (callback != null)
                callback.onFinishEvent(status);
            // # execution stop judge
            // not error-ignore mode and command status code is "error"
            if (!this.isErrorIgnoreExecution() && status == RESULT_ERR)
                break;
        }
    }
}
