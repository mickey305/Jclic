package com.mickey305.util.cli;

import java.util.Collection;

/**
 * Created by K.Misaki on 2017/05/13.
 *
 */
public interface BufferingInterface<T> {
    T add(T command);

    void addAll(Collection<T> commands);

    T undo();

    T redo();
}
