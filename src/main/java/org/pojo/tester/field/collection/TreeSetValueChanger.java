package org.pojo.tester.field.collection;

import java.util.Arrays;
import java.util.TreeSet;

public class TreeSetValueChanger extends CollectionFieldValueChanger<TreeSet<?>> {

    @Override
    public boolean areDifferentValues(final TreeSet<?> sourceValue, final TreeSet<?> targetValue) {
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
    protected TreeSet<?> increaseValue(final TreeSet<?> value, final Class<?> type) {
        return value != null
               ? null
               : new TreeSet<>();
    }
}
