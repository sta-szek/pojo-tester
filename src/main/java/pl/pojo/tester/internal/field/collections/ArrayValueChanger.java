package pl.pojo.tester.internal.field.collections;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.lang.reflect.Array;
import java.util.Arrays;

class ArrayValueChanger extends AbstractFieldValueChanger<Object> {

    @Override
    public boolean areDifferentValues(final Object sourceValue, final Object targetValue) {
        return !Arrays.deepEquals(new Object[]{ sourceValue }, new Object[]{ targetValue });
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.isArray();
    }

    @Override
    protected Object increaseValue(final Object value, final Class<?> type) {
        return value == null
               ? Array.newInstance(type.getComponentType(), 0)
               : null;
    }


}
