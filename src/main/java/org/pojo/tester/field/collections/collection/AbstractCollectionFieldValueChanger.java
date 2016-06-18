package org.pojo.tester.field.collections.collection;


import org.pojo.tester.field.AbstractFieldValueChanger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

public abstract class AbstractCollectionFieldValueChanger<T extends Collection> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new ArrayListValueChanger().register(new DequeValueChanger())
                                                                                        .register(new HashSetValueChanger())
                                                                                        .register(new LinkedHashSetValueChanger())
                                                                                        .register(new LinkedListValueChanger())
                                                                                        .register(new ListValueChanger())
                                                                                        .register(new QueueValueChanger())
                                                                                        .register(new SetValueChanger())
                                                                                        .register(new SortedSetValueChanger())
                                                                                        .register(new StackValueChanger())
                                                                                        .register(new TreeSetValueChanger())
                                                                                        .register(new VectorValueChanger());

    @Override
    public boolean areDifferentValues(final T sourceValue, final T targetValue) {
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
}
