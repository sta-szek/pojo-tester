package pl.pojo.tester.field.collections.map;

import java.util.LinkedHashMap;


class LinkedHashMapValueChanger extends AbstractMapFieldValueChanger<LinkedHashMap<?, ?>> {

    @Override
    protected LinkedHashMap<?, ?> increaseValue(final LinkedHashMap<?, ?> value, final Class<?> type) {
        return value != null
               ? null
               : new LinkedHashMap<>();
    }
}
