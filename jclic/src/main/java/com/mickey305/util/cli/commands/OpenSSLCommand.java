package com.mickey305.util.cli.commands;

import com.mickey305.util.cli.DefaultExecutionImplementor;
import com.mickey305.util.cli.TerminalCommand;
import com.mickey305.util.cli.TerminalCommandBuilder;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public class OpenSSLCommand extends TerminalCommand {
    private static final String CMD_NAME = "openssl";

    public OpenSSLCommand(String commandPath) {
        super(commandPath, CMD_NAME);
    }

    public static OpenSSLCommand create() {
        return TerminalCommandBuilder.build(OpenSSLCommand.class);
    }

    @Override
    protected int executeLogic() {
        return DefaultExecutionImplementor.impl(this);
    }
}
