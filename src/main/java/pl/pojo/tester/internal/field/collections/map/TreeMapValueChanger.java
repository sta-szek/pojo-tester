package pl.pojo.tester.internal.field.collections.map;

import java.util.TreeMap;


class TreeMapValueChanger extends AbstractMapFieldValueChanger<TreeMap<?, ?>> {

    @Override
    protected TreeMap<?, ?> increaseValue(final TreeMap<?, ?> value, final Class<?> type) {
        return value == null
               ? new TreeMap<>()
               : null;
    }
}
