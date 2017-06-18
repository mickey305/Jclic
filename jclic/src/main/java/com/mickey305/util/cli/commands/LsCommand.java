package com.mickey305.util.cli.commands;

import com.mickey305.util.cli.DefaultExecutionImplementor;
import com.mickey305.util.cli.TerminalCommand;
import com.mickey305.util.cli.TerminalCommandBuilder;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public class LsCommand extends TerminalCommand {
    private static final String CMD_NAME = "ls";

    @FunctionalInterface
    public interface Callback {
        void onCreate(boolean status);
    }

    public LsCommand(String commandPath) {
        super(commandPath, CMD_NAME);
    }

    public static LsCommand create() {
        return create(null);
    }

    public static LsCommand create(Callback callback) {
        LsCommand instance = null;
        try {
            instance = TerminalCommandBuilder.build(LsCommand.class);
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
