package com.mickey305.util.cli.commands;

import com.mickey305.util.cli.DefaultExecutionImplementor;
import com.mickey305.util.cli.TerminalCommand;
import com.mickey305.util.cli.TerminalCommandBuilder;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public class LsCommand extends TerminalCommand {
    private static final String CMD_NAME = "ls";

    public LsCommand(String commandPath) {
        super(commandPath, CMD_NAME);
    }

    public static LsCommand create() {
        return TerminalCommandBuilder.build(LsCommand.class);
    }

    @Override
    protected int executeLogic() {
        return DefaultExecutionImplementor.impl(this);
    }
}
