package com.mickey305.util.cli.runtime;

import java.io.IOException;

import static com.mickey305.util.cli.TerminalCommand.RESULT_ERR;

/**
 * Created by K.Misaki on 2017/05/07.
 *
 */
public class CoreExecutor {
    @FunctionalInterface
    public interface Callback {
        int afterExecutionTask(Process process) throws InterruptedException;
    }

    @FunctionalInterface
    public interface Supplier<T> {
        T get() throws IOException;
    }

    public int executeProcessLogic(Supplier<Process> processCreator, Callback callback) {
        int status = RESULT_ERR;
        try {
            Process process = processCreator.get();
            if (callback != null)
                status = callback.afterExecutionTask(process);
            if (process.isAlive())
                process.destroy();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return status;
    }
}
