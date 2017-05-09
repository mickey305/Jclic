package com.mickey305.util.cli;

import java.util.Collection;
import java.util.Stack;

import static com.mickey305.util.cli.Command.RESULT_ERR;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class Invoker<RS> {
    private long step;
    private Stack<Command<RS>> commands;
    private Stack<Command<RS>> trashCommands;

    @FunctionalInterface
    public interface Callback<RS> {
        void onFinishEvent(RS result);
    }

    public Stack<Command<RS>> getCommands() {
        return commands;
    }

    public void setCommands(Stack<Command<RS>> commands) {
        this.commands = commands;
    }

    private Stack<Command<RS>> getTrashCommands() {
        return trashCommands;
    }

    private void setTrashCommands(Stack<Command<RS>> trashCommands) {
        this.trashCommands = trashCommands;
    }

    public Invoker() {
        this.setCommands(new Stack<>());
        this.setTrashCommands(new Stack<>());
        step = 0;
    }

    public Command<RS> addCommand(Command<RS> command) {
        if (!this.getTrashCommands().isEmpty())
            this.setTrashCommands(new Stack<>());
        return this.getCommands().push(command);
    }

    public void addAllCommands(Collection<Command<RS>> commands) {
        commands.forEach(this::addCommand);
    }

    public Command<RS> undoCommand() {
        if (!this.getCommands().isEmpty()) {
            Command<RS> popCommand = this.getCommands().pop();
            this.getTrashCommands().push(popCommand);
            return popCommand;
        } else {
            return null;
        }
    }

    public Command<RS> redoCommand() {
        if (!this.getTrashCommands().isEmpty()) {
            Command<RS> popCommand = this.getTrashCommands().pop();
            this.getCommands().push(popCommand);
            return popCommand;
        } else {
            return null;
        }
    }

    public void execute() {
        this.execute(null);
    }

    public void execute(Callback<RS> callback) {
        for (Command<RS> command : this.getCommands()) {
            RS status = command.execute();

            step++;
//            System.out.println(">>>>>>>>>> EXECUTED STEP [" + step + "] <<<<<<<<<<");

            Receiver receiver = command.getReceiver();

            if (callback != null)
                callback.onFinishEvent(status);
            if (receiver != null
                    && receiver.returnType().equals(Integer.class)
                    && RESULT_ERR == status)
                break;
        }
    }
}
