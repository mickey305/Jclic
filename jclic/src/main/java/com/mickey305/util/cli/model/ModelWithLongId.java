package com.mickey305.util.cli.model;

/**
 * Created by K.Misaki on 2017/05/20.
 *
 */
public abstract class ModelWithLongId {
    private long id;

    public ModelWithLongId(long id) {
        this.setId(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
