package com.mickey305.util.cli.commands;

import com.mickey305.util.cli.Receiver;
import com.mickey305.util.cli.TerminalCommand;
import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.runtime.Executor;

import java.util.ArrayList;
import java.util.List;

import static com.mickey305.util.cli.TerminalCommandUtils.generatePipeByArguments;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public class GrepCommand extends TerminalCommand {
    private static final String CMD_NAME = "grep";

    public GrepCommand(String commandPath) {
        super(commandPath, CMD_NAME);
    }

    @Override
    public int execute() {
        int status;
        List<TerminalCommand> pipeCommands = getPipeCommands();
        List<Arguments> argumentsList = new ArrayList<>();
        Arguments args = getArgs();
        argumentsList.add(args);
        Executor executor = new Executor();

        if (!pipeCommands.isEmpty()) {
            // pipe logic
            pipeCommands.forEach(cmd -> argumentsList.add(cmd.getArgs()));
            String cmdSentence = generatePipeByArguments(argumentsList);
            status = executor.executeRuntime(cmdSentence, process -> {
                createResultSet(process);
                return process.exitValue();
            });
        } else {
            status = executor.executeRuntime(args, process -> {
                createResultSet(process);
                return process.exitValue();
            });
        }


        Receiver receiver = getReceiver();
        if (receiver != null)
            receiver.action(argumentsList, status, getResultSet());

        return status;
    }
}