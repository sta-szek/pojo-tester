package pl.pojo.tester.internal.field.collections;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.util.Arrays;
import java.util.stream.Stream;

class StreamValueChanger extends AbstractFieldValueChanger<Stream<?>> {

    @Override
    public boolean areDifferentValues(final Stream sourceValue, final Stream targetValue) {
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
    protected boolean canChange(final Class<?> type) {
        return type.isAssignableFrom(getGenericTypeClass());
    }

    @Override
    protected Stream<?> increaseValue(final Stream<?> value, final Class<?> type) {
        return value == null
               ? Stream.empty()
               : null;
    }

}
