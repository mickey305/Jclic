package com.mickey305.util.cli.commands;

import com.mickey305.util.cli.DefaultExecutionImplementor;
import com.mickey305.util.cli.TerminalCommand;
import com.mickey305.util.cli.TerminalCommandBuilder;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public class GrepCommand extends TerminalCommand {
    private static final String CMD_NAME = "grep";

    @FunctionalInterface
    public interface Callback {
        void onCreate(boolean status);
    }

    public GrepCommand(String commandPath) {
        super(commandPath, CMD_NAME);
    }

    public static GrepCommand create() {
        return create(null);
    }

    public static GrepCommand create(Callback callback) {
        GrepCommand instance = null;
        try {
            instance = TerminalCommandBuilder.build(GrepCommand.class);
            if (callback != null)
                callback.onCreate(true);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
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
