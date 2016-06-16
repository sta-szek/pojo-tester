package org.pojo.tester.field.collection;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class QueueValueChanger extends CollectionFieldValueChanger<Queue<?>> {

    @Override
    public boolean areDifferentValues(final Queue<?> sourceValue, final Queue<?> targetValue) {
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
    protected Queue<?> increaseValue(final Queue<?> value, final Class<?> type) {
        return value != null
               ? null
               : new LinkedList<>();
    }
}
