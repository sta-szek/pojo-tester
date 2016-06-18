package org.pojo.tester.field.collections.map;

import java.util.TreeMap;


class TreeMapValueChanger extends AbstractMapFieldValueChanger<TreeMap<?, ?>> {

    @Override
    protected TreeMap<?, ?> increaseValue(final TreeMap<?, ?> value, final Class<?> type) {
        return value != null
               ? null
               : new TreeMap<>();
    }
}
