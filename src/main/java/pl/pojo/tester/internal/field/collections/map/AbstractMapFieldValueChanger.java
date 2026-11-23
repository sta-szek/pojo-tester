package pl.pojo.tester.internal.field.collections.map;


import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.util.Map;

public abstract class AbstractMapFieldValueChanger<T extends Map> extends AbstractFieldValueChanger<T> {

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.isAssignableFrom(getGenericTypeClass());
    }

}
