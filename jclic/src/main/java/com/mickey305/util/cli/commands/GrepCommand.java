package com.mickey305.util.cli.commands;

import com.mickey305.util.cli.DefaultExecutionImplementor;
import com.mickey305.util.cli.TerminalCommand;
import com.mickey305.util.cli.TerminalCommandBuilder;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public class GrepCommand extends TerminalCommand {
    private static final String CMD_NAME = "grep";

    public GrepCommand(String commandPath) {
        super(commandPath, CMD_NAME);
    }

    public static GrepCommand create() {
        return TerminalCommandBuilder.build(GrepCommand.class);
    }

    @Override
    protected int executeLogic() {
        return DefaultExecutionImplementor.impl(this);
    }
}
