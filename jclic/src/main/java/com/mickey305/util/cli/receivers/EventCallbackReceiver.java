package com.mickey305.util.cli.receivers;

import com.mickey305.util.cli.CliReceiver;
import com.mickey305.util.cli.model.Arguments;
import com.mickey305.util.cli.model.ResultCache;

import java.util.List;
import java.util.Set;

/**
 * Created by K.Misaki on 2017/07/08.
 *
 */
public class EventCallbackReceiver implements CliReceiver {
    private Callback callback;

    @FunctionalInterface
    public interface Callback {
        void impl(List<Arguments> args, int status, Set<ResultCache<String>> resultSet);
    }

    public EventCallbackReceiver() {}

    public EventCallbackReceiver(Callback callback) {
        this.setCallback(callback);
    }

    @Override
    public void action(List<Arguments> args, int status, Set<ResultCache<String>> resultSet) {
        Callback callback = this.getCallback();
        if (callback != null) callback.impl(args, status, resultSet);
    }

    private Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
