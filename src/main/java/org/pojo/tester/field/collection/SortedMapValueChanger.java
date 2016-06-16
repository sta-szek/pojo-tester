package org.pojo.tester.field.collection;

import java.util.SortedMap;
import java.util.TreeMap;


public class SortedMapValueChanger extends CollectionFieldValueChanger<SortedMap<?, ?>> {

    @Override
    public boolean areDifferentValues(final SortedMap<?, ?> sourceValue, final SortedMap<?, ?> targetValue) {
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
    protected SortedMap<?, ?> increaseValue(final SortedMap<?, ?> value, final Class<?> type) {
        return value != null
               ? null
               : new TreeMap<>();
    }
}
