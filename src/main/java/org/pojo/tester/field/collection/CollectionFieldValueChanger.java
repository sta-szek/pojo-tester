package org.pojo.tester.field.collection;


import org.pojo.tester.field.AbstractFieldValueChanger;

import java.lang.reflect.Field;

public abstract class CollectionFieldValueChanger<T> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new ArrayValueChanger().register(new StreamValueChanger())
                                                                                    .register(new ListValueChanger())
                                                                                    .register(new ArrayListValueChanger());

    @Override
    protected boolean canChange(final Field field) {
        return field.getType()
                    .isAssignableFrom(getGenericTypeClass());
    }
}
