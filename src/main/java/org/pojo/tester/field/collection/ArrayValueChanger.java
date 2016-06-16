package org.pojo.tester.field.collection;

import org.pojo.tester.field.AbstractFieldValueChanger;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class ArrayValueChanger extends AbstractFieldValueChanger<Object> {

    @Override
    public boolean areDifferentValues(final Object sourceValue, final Object targetValue) {
        return !Arrays.deepEquals(new Object[]{sourceValue}, new Object[]{targetValue});
    }

    @Override
    protected boolean canChange(final Field field) {
        SortedSet.class.isAssignableFrom(Set.class); // false
        Set.class.isAssignableFrom(SortedSet.class); // true
        ArrayList.class.isAssignableFrom(Collection.class); // false
        Collection.class.isAssignableFrom(ArrayList.class); // true
        final Collection<String> e = new ArrayList<>();
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
