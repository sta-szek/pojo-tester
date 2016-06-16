package org.pojo.tester.field.collection;

import java.util.Arrays;
import java.util.LinkedList;


public class LinkedListValueChanger extends CollectionFieldValueChanger<LinkedList<?>> {

    @Override
    public boolean areDifferentValues(final LinkedList<?> sourceValue, final LinkedList<?> targetValue) {
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
    protected LinkedList<?> increaseValue(final LinkedList<?> value, final Class<?> type) {
        return value != null
               ? null
               : new LinkedList<>();
    }
}
