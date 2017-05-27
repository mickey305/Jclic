package com.mickey305.util.cli.commands;

import com.mickey305.util.cli.TerminalCommand;
import com.mickey305.util.cli.Receiver;
import com.mickey305.util.cli.TerminalCommandBuilder;
import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.runtime.Executor;

import java.util.ArrayList;
import java.util.List;

import static com.mickey305.util.cli.TerminalCommandUtils.generatePipeByArguments;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public class LsCommand extends TerminalCommand {
    private static final String CMD_NAME = "ls";

    public LsCommand(String commandPath) {
        super(commandPath, CMD_NAME);
    }

    public static LsCommand create() {
        return TerminalCommandBuilder.build(LsCommand.class);
    }

    @Override
    protected int executeLogic() {
        int status;
        List<Arguments> argumentsList = new ArrayList<>();
        Executor executor = new Executor();

        List<TerminalCommand> pipeCommands = getPipeCommands();
        Arguments args = getArgs();

        argumentsList.add(args);

        if (!pipeCommands.isEmpty()) {
            // pipe logic
            pipeCommands.forEach(cmd -> argumentsList.add(cmd.getArgs()));
            String cmdSentence = generatePipeByArguments(argumentsList);
            status = executor.executeRuntime(cmdSentence, process -> {
                createPid(process);
                createResultSet(process);
                return process.exitValue();
            });
        } else {
            status = executor.executeRuntime(args, process -> {
                createPid(process);
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
