package com.mickey305.util.cli;

import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.runtime.Executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import static com.mickey305.util.cli.TerminalCommandUtils.generatePipeByArguments;

/**
 * Created by K.Misaki on 2017/05/27.
 *
 */
public class DefaultExecutionImplementor {
    private static Stack<Process> executedProcessBuf;

    // TODO #1 : test injected method
    private static Stack<Process> getExecutedProcessBuf() {
        if (executedProcessBuf == null)
            executedProcessBuf = new Stack<>();
        return executedProcessBuf;
    }

    // TODO #2 : test injected method
    private static void killAllProcess() {
        getExecutedProcessBuf().forEach(p -> { if (p.isAlive()) p.destroy(); });
    }

    // TODO #3 : test injected method
    private static List<Process> killAllProcessForcibly() {
        return getExecutedProcessBuf().stream().map(Process::destroyForcibly).collect(Collectors.toList());
    }

    public static <C extends TerminalCommand> int impl(C instance) {
        int status;
        List<Arguments> argumentsList = new ArrayList<>();
        Executor executor = new Executor();

        List<TerminalCommand> pipeCommands = instance.getPipeCommands();
        Arguments args = instance.getArgs();

        argumentsList.add(args);

        if (!pipeCommands.isEmpty()) {
            // pipe logic
            pipeCommands.forEach(cmd -> argumentsList.add(cmd.getArgs()));
            String cmdSentence = generatePipeByArguments(argumentsList);
            status = executor.executeRuntime(cmdSentence, process -> {
                /* todo : stack a executed process
                getExecutedProcessBuf().push(process);
                */
                instance.createPid(process);
                instance.createResultSet(process);
                return process.exitValue();
            });
        } else {
            status = executor.executeRuntime(args, process -> {
                /* todo : stack a executed process
                getExecutedProcessBuf().push(process);
                */
                instance.createPid(process);
                instance.createResultSet(process);
                return process.exitValue();
            });
        }

        CliReceiver receiver = instance.getReceiver();
        if (receiver != null)
            receiver.action(argumentsList, status, instance.getResultSet());

        return status;
    }
}
