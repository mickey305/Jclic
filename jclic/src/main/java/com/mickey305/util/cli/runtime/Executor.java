package com.mickey305.util.cli.runtime;

import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.system.OSCheck;

import java.io.File;
import java.util.List;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class Executor extends CoreExecutor {
    private static final int CMD = 0;
    private static final int OPT = 1;
    // shell command literal definition
    private static final String CMD_SHELL = "sh";
    private static final String OPT_SHELL = "-c";
    private static final String CMD_COMMAND_PROMPT = "cmd";
    private static final String OPT_COMMAND_PROMPT = "/c";

    public int executeRuntime(Arguments args, Callback callback) {
        return executeRuntime(args.flatten(), callback);
    }

    public int executeRuntime(Arguments args, String[] envp, Callback callback) {
        return executeRuntime(args.flatten(), envp, callback);
    }

    public int executeRuntime(Arguments args, String[] envp, File dir, Callback callback) {
        return executeRuntime(args.flatten(), envp, dir, callback);
    }

    public int executeRuntime(String args, Callback callback) {
        final Runtime runtime = Runtime.getRuntime();
        return executeProcessLogic(() -> runtime.exec(generateCmdAry(args)), callback);
    }

    public int executeRuntime(String args, String[] envp, Callback callback) {
        final Runtime runtime = Runtime.getRuntime();
        return executeProcessLogic(() -> runtime.exec(generateCmdAry(args), envp), callback);
    }

    public int executeRuntime(String args, String[] envp, File dir, Callback callback) {
        final Runtime runtime = Runtime.getRuntime();
        return executeProcessLogic(() -> runtime.exec(generateCmdAry(args), envp, dir), callback);
    }

    public int executeProcess(String args, Callback callback) {
        return executeProcess(new ProcessBuilder(generateCmdAry(args)), callback);
    }

    public int executeProcess(String[] args, Callback callback) {
        return executeProcess(new ProcessBuilder(args), callback);
    }

    public int executeProcess(List<String> args, Callback callback) {
        return executeProcess(new ProcessBuilder(args), callback);
    }

    public int executeProcess(ProcessBuilder pb, Callback callback) {
        return executeProcessLogic(pb::start, callback);
    }

    private String[] generateCmdAry(String args) {
        // Unix
        String[] cmd = {CMD_SHELL, OPT_SHELL, args};
        // Windows
        if (OSCheck.isWindows()) {
            cmd[CMD] = CMD_COMMAND_PROMPT;
            cmd[OPT] = OPT_COMMAND_PROMPT;
        }
        return cmd;
    }
}
