package org.pojo.tester.field.collection;

import java.util.HashMap;


public class HashMapValueChanger extends CollectionFieldValueChanger<HashMap<?, ?>> {

    @Override
    public boolean areDifferentValues(final HashMap<?, ?> sourceValue, final HashMap<?, ?> targetValue) {
        if (sourceValue == targetValue) {
            return false;
        }
        if (sourceValue == null || targetValue == null) {
            return true;
        } else {
            targetValue.forEach(sourceValue::remove);
            return sourceValue.size() != 0;
        }
    }

    @Override
    protected HashMap<?, ?> increaseValue(final HashMap<?, ?> value, final Class<?> type) {
        return value != null
               ? null
               : new HashMap<>();
    }
}
