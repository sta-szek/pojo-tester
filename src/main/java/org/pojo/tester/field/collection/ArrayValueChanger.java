package org.pojo.tester.field.collection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ArrayValueChanger extends CollectionFieldValueChanger<Object> {

    @Override
    public boolean areDifferentValues(final Object sourceValue, final Object targetValue) {
        return !Arrays.deepEquals(new Object[]{sourceValue}, new Object[]{targetValue});
    }

    @Override
    protected boolean canChange(final Field field) {
        return field.getType()
                    .isArray();
    }

    @Override
    protected Object increaseValue(final Object value, final Class<?> type) {
        return value != null
               ? null
               : Array.newInstance(type.getComponentType(), 0);
    }


}
