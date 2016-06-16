package org.pojo.tester.field.collection;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class LinkedHashSetValueChanger extends CollectionFieldValueChanger<LinkedHashSet<?>> {

    @Override
    public boolean areDifferentValues(final LinkedHashSet<?> sourceValue, final LinkedHashSet<?> targetValue) {
        if (sourceValue == targetValue) {
            return false;
        }
        if (sourceValue == null || targetValue == null) {
            return true;
        } else {
            final Object[] sourceValuesArray = sourceValue.toArray();
            final Object[] targetValuesArray = targetValue.toArray();
            return !Arrays.deepEquals(sourceValuesArray, targetValuesArray);
        }
    }

    @Override
    protected LinkedHashSet<?> increaseValue(final LinkedHashSet<?> value, final Class<?> type) {
        return value != null
               ? null
               : new LinkedHashSet<>();
    }
}
