package org.pojo.tester.field.collection;

import java.util.LinkedHashMap;


public class LinkedHashMapValueChanger extends CollectionFieldValueChanger<LinkedHashMap<?, ?>> {

    @Override
    public boolean areDifferentValues(final LinkedHashMap<?, ?> sourceValue, final LinkedHashMap<?, ?> targetValue) {
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
    protected LinkedHashMap<?, ?> increaseValue(final LinkedHashMap<?, ?> value, final Class<?> type) {
        return value != null
               ? null
               : new LinkedHashMap<>();
    }
}
