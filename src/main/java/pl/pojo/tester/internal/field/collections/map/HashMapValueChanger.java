package pl.pojo.tester.internal.field.collections.map;

import java.util.HashMap;


class HashMapValueChanger extends AbstractMapFieldValueChanger<HashMap<?, ?>> {

    @Override
    protected HashMap<?, ?> increaseValue(final HashMap<?, ?> value, final Class<?> type) {
        return value != null
               ? null
               : new HashMap<>();
    }
}
