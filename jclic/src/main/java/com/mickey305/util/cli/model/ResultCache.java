package com.mickey305.util.cli.model;

import java.util.function.UnaryOperator;

/**
 * Created by K.Misaki on 2017/05/05.
 *
 */
public class ResultCache<T> extends GenericCloneableEntity<T> {
    public static boolean SHALLOW_COPY = true;

    private ResultType type;
    private T result;

    public ResultCache(ResultType type, T result) {
        this(type, result, null);
    }

    public ResultCache(ResultType type, T result, UnaryOperator<T> cloneOperator) {
        this.setType(type);
        this.setResult(result);
        super.setCloneOperator(cloneOperator);
    }

    @Override
    public ResultCache<T> clone() {
        return this.clone(super.getCloneOperator());
    }

    public ResultCache<T> clone(UnaryOperator<T> cloneOperator) {
        ResultCache<T> scope;
        scope = (ResultCache<T>) super.clone();
        // manual scoping
        scope.setType(this.getType());
        scope.setResult((cloneOperator != null)
                ? cloneOperator.apply(this.getResult())
                : (SHALLOW_COPY) ? this.getResult() : null);
        return scope;
    }

    public ResultCache<T> cloneOperator(UnaryOperator<T> cloneOperator) {
        super.setCloneOperator(cloneOperator);
        return this;
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
