package org.pojo.tester.field.collection;

import java.util.Arrays;
import java.util.Stack;

public class StackValueChanger extends CollectionFieldValueChanger<Stack<?>> {

    @Override
    public boolean areDifferentValues(final Stack<?> sourceValue, final Stack<?> targetValue) {
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
    protected Stack<?> increaseValue(final Stack<?> value, final Class<?> type) {
        return value != null
               ? null
               : new Stack<>();
    }
}
