package com.mickey305.util.cli.model;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class ResultCache<T> {
    private ResultType type;
    private T result;

    public ResultCache(ResultType type, T result) {
        this.setType(type);
        this.setResult(result);
    }

    public ResultType getType() {
        return type;
    }

    public void setType(ResultType type) {
        this.type = type;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
