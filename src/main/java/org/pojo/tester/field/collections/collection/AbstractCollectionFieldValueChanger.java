package org.pojo.tester.field.collections.collection;


import org.pojo.tester.field.AbstractFieldValueChanger;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;

public abstract class AbstractCollectionFieldValueChanger<T extends Collection> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new ArrayListValueChanger().attachNext(new DequeValueChanger())
                                                                                        .attachNext(new HashSetValueChanger())
                                                                                        .attachNext(new LinkedHashSetValueChanger())
                                                                                        .attachNext(new LinkedListValueChanger())
                                                                                        .attachNext(new ListValueChanger())
                                                                                        .attachNext(new QueueValueChanger())
                                                                                        .attachNext(new SetValueChanger())
                                                                                        .attachNext(new SortedSetValueChanger())
                                                                                        .attachNext(new StackValueChanger())
                                                                                        .attachNext(new TreeSetValueChanger())
                                                                                        .attachNext(new VectorValueChanger());

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

    @Override
    protected Class<T> getGenericTypeClass() {
        return (Class<T>) ((ParameterizedTypeImpl) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0])
                .getRawType();
    }
}
