package com.mickey305.util.cli.commands;

import com.mickey305.util.cli.Command;
import com.mickey305.util.cli.Receiver;
import com.mickey305.util.cli.model.Arguments;

import java.io.File;
import java.io.IOException;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public class WhichCommand<R> extends Command<R> {
    private static final String CMD_NAME = "which";

    private String commandPath;

    public String getCommandPath() {
        return commandPath;
    }

    public void setCommandPath(String commandPath) {
        this.commandPath = commandPath;
    }

    public WhichCommand(String commandPath) {
        File targetCommand = new File(commandPath);
        if (targetCommand.exists() && targetCommand.isFile()
                && targetCommand.canExecute() && commandPath.endsWith(CMD_NAME)) {
            try {
                this.setCommandPath(targetCommand.getCanonicalPath());
            } catch (IOException e) {
                this.throwException(commandPath, e);
            }
        } else {
            this.throwException(commandPath, null);
        }
    }

    @Override
    public R execute() {
        Arguments args = new Arguments(this.getCommandPath());

        Receiver<R> receiver = getReceiver();
        return (receiver == null)
                ? null
                : receiver.action(args);
    }

    private void throwException(String commandPath, Throwable th) {
        String cause = (th != null)
                ? System.lineSeparator() + "caused by " + th.getClass().getName()
                : "";
        throw new IllegalArgumentException("command PATH exception: please check the input path name."
                + System.lineSeparator() + "input = [" + commandPath + "]" + cause);
    }
}
