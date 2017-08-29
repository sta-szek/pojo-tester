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
     * All returned lists are read-only and backed by a copy of the original list, so later changes
     * in the original list will not affect the returned sublists.
     *
     * @param list list of objects
     * @param <T>   the type of list element
     * @return sublists of list
     */
    public static <T> List<List<T>> subsequences(final List<T> list) {
        final List<T> copyOfList = new ArrayList<>(list);
        final List<List<T>> res = new ArrayList<>();
        for (int i = 0; i < copyOfList.size(); i++) {
            res.add(Collections.unmodifiableList(copyOfList.subList(i, copyOfList.size())));
        }
        return Collections.unmodifiableList(res);
    }
}
