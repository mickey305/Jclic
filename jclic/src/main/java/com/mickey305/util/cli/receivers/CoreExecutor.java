package com.mickey305.util.cli.receivers;

import java.io.IOException;

import static com.mickey305.util.cli.Command.RESULT_ERR;

/**
 * Created by K.Misaki on 2017/05/07.
 *
 */
public interface CoreExecutor<R> {
    @FunctionalInterface
    interface Callback<R> {
        R afterExecutionTask(Process process) throws InterruptedException;
    }

    @FunctionalInterface
    interface Supplier<T> {
        T get() throws IOException;
    }

    @SuppressWarnings("unchecked")
    default R executeProcessLogic(Supplier<Process> processCreator, Callback<R> callback) {
        R status = (R) RESULT_ERR;
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
