package org.pojo.tester.field.collections;

import org.pojo.tester.field.AbstractFieldValueChanger;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
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
    protected boolean canChange(final Field field) {
        return field.getType()
                    .isAssignableFrom(getGenericTypeClass());
    }

    @Override
    protected Stream<?> increaseValue(final Stream<?> value, final Class<?> type) {
        return value != null
               ? null
               : Stream.empty();
    }

    @Override
    protected Class<Stream<?>> getGenericTypeClass() {
        return (Class<Stream<?>>) ((ParameterizedTypeImpl) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments
                ()[0])
                .getRawType();
    }
}
