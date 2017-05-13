package com.mickey305.util.cli;

import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.model.ResultCache;
import com.mickey305.util.cli.model.ResultType;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mickey305.util.cli.Command.RESULT_ERR;
import static com.mickey305.util.cli.Command.RESULT_OK;

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

    public static boolean containsStdout(Set<ResultCache<String>> resultSet) {
        return contains(ResultType.STANDARD, resultSet);
    }

    public static boolean containsWarning(Set<ResultCache<String>> resultSet) {
        return contains(ResultType.WARNING, resultSet);
    }

    public static boolean containsError(Set<ResultCache<String>> resultSet) {
        return contains(ResultType.ERROR, resultSet);
    }

    private static boolean contains(final ResultType type, final Set<ResultCache<String>> resultSet) {
        long errCnt = resultSet.stream().filter(rs -> rs.getType() == type).count();
        return (errCnt != 0);
    }

    public static int errorOr(int status, Set<ResultCache<String>> resultSet) {
        if (status == RESULT_ERR)
            return RESULT_ERR;

        if (containsError(resultSet))
            return RESULT_ERR;

        return RESULT_OK;
    }

    public static int errorAnd(int status, Set<ResultCache<String>> resultSet) {
        if (status == RESULT_ERR && containsError(resultSet))
            return RESULT_ERR;

        return RESULT_OK;
    }
}
