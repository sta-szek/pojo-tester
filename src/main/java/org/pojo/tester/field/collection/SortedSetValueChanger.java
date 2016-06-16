package org.pojo.tester.field.collection;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetValueChanger extends CollectionFieldValueChanger<SortedSet<?>> {

    @Override
    public boolean areDifferentValues(final SortedSet<?> sourceValue, final SortedSet<?> targetValue) {
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
    protected SortedSet<?> increaseValue(final SortedSet<?> value, final Class<?> type) {
        return value != null
               ? null
               : new TreeSet<>();
    }
}
