package com.mickey305.util.cli;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public abstract class Command implements Cloneable {
    public static final int RESULT_OK = 0;
    public static final int RESULT_ERR = 1;

    private Receiver receiver;

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
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
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return scope;
    }

    public abstract int execute();
}
