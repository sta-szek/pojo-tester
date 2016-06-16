package org.pojo.tester.field.collection;

import org.pojo.tester.field.AbstractFieldValueChanger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

public class StreamValueChanger extends AbstractFieldValueChanger<Stream<?>> {

    @Override
    public boolean areDifferentValues(final Stream<?> sourceValue, final Stream<?> targetValue) {
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
    protected boolean canChange(final Field field) {
        return field.getType()
                    .isAssignableFrom(Stream.class);
    }

    @Override
    protected Stream<?> increaseValue(final Stream<?> value, final Class<?> type) {
        return value != null
               ? null
               : Stream.empty();
    }
}
