package org.pojo.tester.field.collection;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListValueChanger extends CollectionFieldValueChanger<ArrayList<?>> {

    @Override
    public boolean areDifferentValues(final ArrayList<?> sourceValue, final ArrayList<?> targetValue) {
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
    protected ArrayList<?> increaseValue(final ArrayList<?> value, final Class<?> type) {
        return value != null
               ? null
               : new ArrayList<>();
    }
}
