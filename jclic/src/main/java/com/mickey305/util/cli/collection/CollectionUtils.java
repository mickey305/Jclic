package com.mickey305.util.cli.collection;

import javafx.util.Pair;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ObjLongConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by K.Misaki on 2017/05/07.
 *
 */
public class CollectionUtils {
    public static <A, B, R> R zip(final List<A> aList, final List<B> bList,
                                                 Function<List<Pair<A, B>>, R> action) {
        Objects.nonNull(action);

        List<Pair<A, B>> pairs = IntStream.range(0, Math.min(aList.size(), bList.size()))
                .mapToObj(i -> new Pair<>(aList.get(i), bList.get(i)))
                .collect(Collectors.toList());
        return action.apply(pairs);
    }

    public static <T> void eachWithIndex(Iterable<T> iterable, ObjLongConsumer<T> action) {
        long index = 0;
        for (T target : iterable) {
            action.accept(target, index++);
        }
    }
}
