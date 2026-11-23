package pl.pojo.tester.internal.field.collections.collection;


import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.util.Collection;

public abstract class AbstractCollectionFieldValueChanger<T extends Collection> extends AbstractFieldValueChanger<T> {

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.isAssignableFrom(getGenericTypeClass());
    }

}
