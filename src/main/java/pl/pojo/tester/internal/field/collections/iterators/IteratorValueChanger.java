package pl.pojo.tester.internal.field.collections.iterators;


import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import org.apache.commons.collections4.IteratorUtils;

class IteratorValueChanger extends AbstractIteratorsFieldValueChanger<Iterator<?>> {


    @Override
    public boolean areDifferentValues(final Iterator<?> sourceValue, final Iterator<?> targetValue) {
        if (sourceValue == targetValue) {
            return false;
        }
        if (sourceValue == null || targetValue == null) {
            return true;
        } else {

            final Object[] sourceValuesArray = IteratorUtils.toArray(sourceValue);
            final Object[] targetValuesArray = IteratorUtils.toArray(targetValue);
            return !Arrays.deepEquals(sourceValuesArray, targetValuesArray);
        }
    }

    @Override
    protected Iterator<?> increaseValue(final Iterator<?> value, final Class<?> type) {
        return value != null
               ? null
               : Collections.EMPTY_LIST.iterator();
    }
}
