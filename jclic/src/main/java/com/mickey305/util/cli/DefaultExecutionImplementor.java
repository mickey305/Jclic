package com.mickey305.util.cli;

import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.runtime.Executor;

import java.util.ArrayList;
import java.util.List;

import static com.mickey305.util.cli.TerminalCommandUtils.generatePipeByArguments;

/**
 * Created by K.Misaki on 2017/05/27.
 *
 */
public class DefaultExecutionImplementor {
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
                instance.createPid(process);
                instance.createResultSet(process);
                return process.exitValue();
            });
        } else {
            status = executor.executeRuntime(args, process -> {
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
