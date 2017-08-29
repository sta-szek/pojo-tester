package pl.pojo.tester.internal.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Sublists {
    private Sublists() {
    }
    /**
     * Given a list of objects, return a list of sublists, i-th sublist includes i-th element of
     * the original list and all elements that follow it in the original list.
     *
     * @param list0 list of objects
     * @param <T>   the type of list element
     * @return list of sublists of list0
     */
    public static <T> List<List<T>> subsequences(final List<T> list0) {
        final List<T> list = new ArrayList<>(list0);
        final List<List<T>> res = new ArrayList<>();
        for (int i = 0,
             n = list.size(); i < n; i++) {
            res.add(Collections.unmodifiableList(list.subList(i, n)));
        }
        return Collections.unmodifiableList(res);
    }
}
