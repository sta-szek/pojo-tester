package org.pojo.tester.field.collection;

import java.util.Arrays;
import java.util.Vector;

public class VectorValueChanger extends CollectionFieldValueChanger<Vector<?>> {

    @Override
    public boolean areDifferentValues(final Vector<?> sourceValue, final Vector<?> targetValue) {
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
    protected Vector<?> increaseValue(final Vector<?> value, final Class<?> type) {
        return value != null
               ? null
               : new Vector<>();
    }
}
