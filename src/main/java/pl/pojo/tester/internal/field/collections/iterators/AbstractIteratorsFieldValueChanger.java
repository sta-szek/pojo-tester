package pl.pojo.tester.internal.field.collections.iterators;


import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

public abstract class AbstractIteratorsFieldValueChanger<T> extends AbstractFieldValueChanger<T> {

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.isAssignableFrom(getGenericTypeClass());
    }

}
