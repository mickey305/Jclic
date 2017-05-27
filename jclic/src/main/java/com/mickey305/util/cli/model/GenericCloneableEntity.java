package com.mickey305.util.cli.model;

import java.util.function.UnaryOperator;

/**
 * Created by K.Misaki on 2017/05/27.
 *
 */
public abstract class GenericCloneableEntity<T> implements Cloneable {
    private UnaryOperator<T> cloneOperator;

    @Override
    @SuppressWarnings("unchecked")
    public GenericCloneableEntity<T> clone() {
        try {
            GenericCloneableEntity<T> scope;
            scope = (GenericCloneableEntity<T>) super.clone();
            // manual scoping
            scope.setCloneOperator(this.getCloneOperator());
            return scope;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected UnaryOperator<T> getCloneOperator() {
        return cloneOperator;
    }

    protected void setCloneOperator(UnaryOperator<T> cloneOperator) {
        this.cloneOperator = cloneOperator;
    }
}
