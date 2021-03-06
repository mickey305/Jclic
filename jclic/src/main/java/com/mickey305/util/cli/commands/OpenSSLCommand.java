package com.mickey305.util.cli.commands;

import com.mickey305.util.cli.DefaultExecutionImplementor;
import com.mickey305.util.cli.TerminalCommand;
import com.mickey305.util.cli.TerminalCommandBuilder;
import com.mickey305.util.system.OSCheck;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public class OpenSSLCommand extends TerminalCommand {
    private static final String CMD_NAME = OSCheck.isWindows() ? "openssl.exe": "openssl";

    @FunctionalInterface
    public interface Callback {
        void onCreate(boolean status);
    }

    public OpenSSLCommand(String commandPath) {
        super(commandPath, CMD_NAME);
    }

    public static OpenSSLCommand create() {
        return create(null);
    }

    public static OpenSSLCommand create(Callback callback) {
        OpenSSLCommand instance = null;
        try {
            instance = TerminalCommandBuilder.build(OpenSSLCommand.class);
            if (callback != null)
                callback.onCreate(true);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            if (callback != null)
                callback.onCreate(false);
        }
        return instance;
    }

    @Override
    protected int executeLogic() {
        return DefaultExecutionImplementor.impl(this);
    }
}
