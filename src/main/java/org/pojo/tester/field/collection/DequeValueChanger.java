package org.pojo.tester.field.collection;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class DequeValueChanger extends CollectionFieldValueChanger<Deque<?>> {

    @Override
    public boolean areDifferentValues(final Deque<?> sourceValue, final Deque<?> targetValue) {
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
    protected Deque<?> increaseValue(final Deque<?> value, final Class<?> type) {
        return value != null
               ? null
               : new LinkedList<>();
    }
}
