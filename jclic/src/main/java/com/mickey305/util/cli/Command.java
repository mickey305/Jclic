package com.mickey305.util.cli;

import com.mickey305.util.cli.model.Benchmark;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public abstract class Command implements Cloneable {
    public static final int RESULT_OK = 0;
    public static final int RESULT_ERR = 1;

    private Receiver receiver;
    private Map<Benchmark, Timestamp> timestampMaps;

    public Command() {
        this.setTimestampMaps(new HashMap<>());
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Map<Benchmark, Timestamp> getTimestampMaps() {
        return timestampMaps;
    }

    private void setTimestampMaps(Map<Benchmark, Timestamp> timestampMaps) {
        this.timestampMaps = timestampMaps;
    }

    public Command receiver(Receiver receiver) {
        this.setReceiver(receiver);
        return this;
    }

    @Override
    public Command clone() {
        Command scope = null;
        try {
            scope = (Command) super.clone();
            scope.timestampMaps = new HashMap<>(this.getTimestampMaps());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return scope;
    }

    public int execute() {
        Map<Benchmark, Timestamp> tsMap = this.getTimestampMaps();
        tsMap.put(Benchmark.START, new Timestamp(System.currentTimeMillis()));
        // execution logic impl
        int status = this.executeLogic();
        tsMap.put(Benchmark.END, new Timestamp(System.currentTimeMillis()));
        return status;
    }

    protected abstract int executeLogic();
}
