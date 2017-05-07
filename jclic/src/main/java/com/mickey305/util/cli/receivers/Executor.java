package com.mickey305.util.cli.receivers;

import com.mickey305.util.cli.model.Arguments;

import java.io.File;
import java.util.List;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public interface Executor<R> extends CoreExecutor<R> {
    default R executeRuntime(Arguments args, Callback<R> callback) {
        return executeRuntime(args.flatten(), callback);
    }

    default R executeRuntime(Arguments args, String[] envp, Callback<R> callback) {
        return executeRuntime(args.flatten(), envp, callback);
    }

    default R executeRuntime(Arguments args, String[] envp, File dir, Callback<R> callback) {
        return executeRuntime(args.flatten(), envp, dir, callback);
    }

    default R executeRuntime(String args, Callback<R> callback) {
        final Runtime runtime = Runtime.getRuntime();
        return executeProcessLogic(() -> runtime.exec(args), callback);
    }

    default R executeRuntime(String args, String[] envp, Callback<R> callback) {

        final Runtime runtime = Runtime.getRuntime();
        return executeProcessLogic(() -> runtime.exec(args, envp), callback);
    }

    default R executeRuntime(String args, String[] envp, File dir, Callback<R> callback) {
        final Runtime runtime = Runtime.getRuntime();
        return executeProcessLogic(() -> runtime.exec(args, envp, dir), callback);
    }

    default R executeProcess(List<String> args, Callback<R> callback) {
        return executeProcess(new ProcessBuilder(args), callback);
    }

    default R executeProcess(ProcessBuilder pb, Callback<R> callback) {
        return executeProcessLogic(pb::start, callback);
    }
}
