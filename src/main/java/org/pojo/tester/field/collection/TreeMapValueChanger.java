package org.pojo.tester.field.collection;

import java.util.TreeMap;


public class TreeMapValueChanger extends CollectionFieldValueChanger<TreeMap<?, ?>> {

    @Override
    public boolean areDifferentValues(final TreeMap<?, ?> sourceValue, final TreeMap<?, ?> targetValue) {
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
    protected TreeMap<?, ?> increaseValue(final TreeMap<?, ?> value, final Class<?> type) {
        return value != null
               ? null
               : new TreeMap<>();
    }
}
