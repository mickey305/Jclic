package com.mickey305.util.cli.model;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class ResultCache<T> implements Cloneable {
    private ResultType type;
    private T result;

    public ResultCache(ResultType type, T result) {
        this.setType(type);
        this.setResult(result);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResultCache<T> clone() {
        ResultCache<T> scope = null;
        try {
            scope = (ResultCache<T>) super.clone();
            scope.setType(this.getType());
            // manual scoping
            scope.setResult(this.getResult());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return scope;
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
