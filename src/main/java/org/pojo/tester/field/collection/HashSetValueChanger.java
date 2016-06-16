package org.pojo.tester.field.collection;

import java.util.Arrays;
import java.util.HashSet;

public class HashSetValueChanger extends CollectionFieldValueChanger<HashSet<?>> {

    @Override
    public boolean areDifferentValues(final HashSet<?> sourceValue, final HashSet<?> targetValue) {
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
    protected HashSet<?> increaseValue(final HashSet<?> value, final Class<?> type) {
        return value != null
               ? null
               : new HashSet<>();
    }
}
