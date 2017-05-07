package com.mickey305.util.cli.receivers.which;

import com.mickey305.util.cli.Receiver;
import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.receivers.Executor;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class JavaReceiver extends Receiver<Integer> implements Executor<Integer> {
    @Override
    public Integer action(Arguments args) {

        args.putOption("java", null);

        return this.executeRuntime(args, process -> {
            createResultSet(process);
            return process.exitValue();
        });
    }
}
