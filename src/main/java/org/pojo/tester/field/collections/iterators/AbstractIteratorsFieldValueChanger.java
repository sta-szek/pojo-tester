package org.pojo.tester.field.collections.iterators;


import java.lang.reflect.Field;
import org.pojo.tester.field.AbstractFieldValueChanger;

public abstract class AbstractIteratorsFieldValueChanger<T> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new IteratorValueChanger().attachNext(new IterableValueChanger());


    @Override
    protected boolean canChange(final Field field) {
        return field.getType()
                    .isAssignableFrom(getGenericTypeClass());
    }

}
