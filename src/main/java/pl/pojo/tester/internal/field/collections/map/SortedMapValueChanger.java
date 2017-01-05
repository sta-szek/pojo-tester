package pl.pojo.tester.internal.field.collections.map;

import java.util.SortedMap;
import java.util.TreeMap;


class SortedMapValueChanger extends AbstractMapFieldValueChanger<SortedMap<?, ?>> {

    @Override
    protected SortedMap<?, ?> increaseValue(final SortedMap<?, ?> value, final Class<?> type) {
        return value == null
               ? new TreeMap<>()
               : null;
    }
}
