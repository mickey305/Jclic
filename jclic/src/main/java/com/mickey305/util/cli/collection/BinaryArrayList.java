package com.mickey305.util.cli.collection;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Created by K.Misaki on 2017/05/07.
 *
 */
public class BinaryArrayList<A, B> extends ArrayList<Pair<A, B>> {
    public BinaryArrayList(List<Pair<A, B>> list) {
        super(list);
    }

    public BinaryArrayList() {
        super();
    }

    public BinaryArrayList(int capacity) {
        super(capacity);
    }

    @SuppressWarnings("unchecked")
    public static <A, B> BinaryArrayList<A, B> createFromList(List<Pair<A, B>> list) {
         return new BinaryArrayList(list);
    }

    public void forEach(final BiConsumer<? super A, ? super B> action) {
        Objects.nonNull(action);

        super.forEach(pair -> {
            A obj1 = pair.getKey();
            B obj2 = pair.getValue();
            action.accept(obj1, obj2);
        });
    }
}
