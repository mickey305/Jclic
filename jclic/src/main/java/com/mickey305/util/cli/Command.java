package com.mickey305.util.cli;

/**
 * Created by K.Misaki on 2017/05/04.
 *
 */
public abstract class Command<R> {
    public static final Integer RESULT_OK = 0;
    public static final Integer RESULT_ERR = 1;

    private Receiver<R> receiver;

    public Receiver<R> getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver<R> receiver) {
        this.receiver = receiver;
    }

    public Command<R> receiver(Receiver<R> receiver) {
        this.setReceiver(receiver);
        return this;
    }

    public abstract R execute();
}
