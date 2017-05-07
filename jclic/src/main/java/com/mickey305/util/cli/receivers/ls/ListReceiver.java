package com.mickey305.util.cli.receivers.ls;

import com.mickey305.util.cli.Receiver;
import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.receivers.Executor;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class ListReceiver extends Receiver<Void> implements Executor<Void> {
    @Override
    public Void action(Arguments args) {

        args.putOption("-la", null);
        args.putOption(".", null);

        return this.executeRuntime(args, process -> {
            createResultSet(process);
            return null;
        });
    }

}
