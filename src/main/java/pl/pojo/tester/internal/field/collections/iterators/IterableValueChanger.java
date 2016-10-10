package pl.pojo.tester.internal.field.collections.iterators;


import org.apache.commons.collections4.IteratorUtils;

import java.util.Arrays;
import java.util.Collections;

class IterableValueChanger extends AbstractIteratorsFieldValueChanger<Iterable<?>> {

    @Override
    public boolean areDifferentValues(final Iterable<?> sourceValue, final Iterable<?> targetValue) {
        if (sourceValue == targetValue) {
            return false;
        }
        if (sourceValue == null || targetValue == null) {
            return true;
        } else {

            final Object[] sourceValuesArray = IteratorUtils.toArray(sourceValue.iterator());
            final Object[] targetValuesArray = IteratorUtils.toArray(targetValue.iterator());
            return !Arrays.deepEquals(sourceValuesArray, targetValuesArray);
        }
    }

    @Override
    protected Iterable<?> increaseValue(final Iterable<?> value, final Class<?> type) {
        return value == null
               ? Collections.EMPTY_LIST
               : null;
    }
}
