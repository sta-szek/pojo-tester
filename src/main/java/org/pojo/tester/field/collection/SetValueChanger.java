package org.pojo.tester.field.collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class SetValueChanger extends CollectionFieldValueChanger<Set<?>> {

    @Override
    public boolean areDifferentValues(final Set<?> sourceValue, final Set<?> targetValue) {
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
    protected Set<?> increaseValue(final Set<?> value, final Class<?> type) {
        return value != null
               ? null
               : Collections.EMPTY_SET;
    }
}
