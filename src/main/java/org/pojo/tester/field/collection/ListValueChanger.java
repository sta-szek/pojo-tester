package org.pojo.tester.field.collection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListValueChanger extends CollectionFieldValueChanger<List<?>> {

    @Override
    public boolean areDifferentValues(final List<?> sourceValue, final List<?> targetValue) {
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
    protected List<?> increaseValue(final List<?> value, final Class<?> type) {
        return value != null
               ? null
               : Collections.EMPTY_LIST;
    }
}
