package com.mickey305.util.cli;

import com.mickey305.util.cli.model.Arguments;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by K.Misaki on 2017/05/13.
 *
 */
public class TerminalCommandUtils {
    public static String generatePipeCmdSentence(final String... args) {
        if (args == null || args.length == 0)
            return null;

        StringBuilder res = new StringBuilder();
        res.append(args[0]);
        for (int i = 1; i < args.length; i++) {
            res.append(" | ").append(args[i]);
        }
        return res.toString();
    }

    public static String generatePipeCmdSentence(final Collection<String> argCol) {
        String[] args = argCol.toArray(new String[0]);
        return generatePipeCmdSentence(args);
    }

    public static String generatePipeByArguments(final Collection<Arguments> args) {
        List<String> argsList = args.stream()
                .map(Arguments::flatten)
                .collect(Collectors.toList());
        return generatePipeCmdSentence(argsList);
    }

    public static String generatePipeByArguments(final Arguments... args) {
        List<Arguments> argsList = Arrays.asList(args);
        return generatePipeCmdSentence(argsList.stream()
                .map(Arguments::flatten)
                .collect(Collectors.toList()));
    }
}
