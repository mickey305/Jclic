package com.mickey305.util.cli.receivers;

import com.mickey305.util.cli.CliReceiver;
import com.mickey305.util.cli.TerminalCommandUtils;
import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.model.ResultCache;
import com.mickey305.util.cli.model.ResultType;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static com.mickey305.util.cli.collection.CollectionUtils.eachWithIndex;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class ExecutionShowReceiver implements CliReceiver {
    @Override
    public void action(List<Arguments> args, int status, Set<ResultCache<String>> resultSet) {
        String argSentence = TerminalCommandUtils.generatePipeByArguments(args);
        final String inHead     = "execution command => ";
        final String outStdHead = "standard output   => ";
        final String errStdHead = "error output      => ";
        final String blankHead  = "                     ";
        final BiConsumer<Long, String> printlnStd = (index, line) -> {
            if (index == 0) System.out.println(outStdHead + line);
            else System.out.println(blankHead + line);};
        final BiConsumer<Long, String> printlnErr = (index, line) -> {
            if (index == 0) System.out.println(errStdHead + line);
            else System.out.println(blankHead + line);};
        List<ResultCache<String>> stdList = resultSet.stream()
                .filter(res -> res.getType() == ResultType.STANDARD)
                .collect(Collectors.toList());
        List<ResultCache<String>> errList = resultSet.stream()
                .filter(res -> res.getType() == ResultType.ERROR)
                .collect(Collectors.toList());

        System.out.println("+--------------------------- Execution Show --------------------------------+");
        System.out.println(inHead + argSentence);
        eachWithIndex(stdList, (res, index) -> printlnStd.accept(index, res.getResult()));
        eachWithIndex(errList, (res, index) -> printlnErr.accept(index, res.getResult()));
        System.out.println("+---------------------------------------------------------------------------+");
    }
}
