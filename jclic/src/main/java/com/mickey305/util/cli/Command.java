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
    private Map<Benchmark, Timestamp> timestampMap;

    public Command() {
        this.setTimestampMap(new HashMap<>());
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Map<Benchmark, Timestamp> getTimestampMap() {
        return timestampMap;
    }

    private void setTimestampMap(Map<Benchmark, Timestamp> timestampMap) {
        this.timestampMap = timestampMap;
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
            scope.timestampMap = new HashMap<>(this.getTimestampMap());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return scope;
    }

    public int execute() {
        Map<Benchmark, Timestamp> tsMap = this.getTimestampMap();
        tsMap.put(Benchmark.START, new Timestamp(System.currentTimeMillis()));
        // execution logic impl
        int status = this.executeLogic();
        tsMap.put(Benchmark.END, new Timestamp(System.currentTimeMillis()));
        return status;
    }

    protected abstract int executeLogic();
}
