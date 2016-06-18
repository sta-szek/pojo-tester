package org.pojo.tester.field.collections.iterators;


import org.pojo.tester.field.AbstractFieldValueChanger;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractIteratorsFieldValueChanger<T> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new IteratorValueChanger().register(new IterableValueChanger());


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
