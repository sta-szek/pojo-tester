package pl.pojo.tester.internal.field.collections.collection;

import pl.pojo.tester.internal.utils.CollectionUtils;

import java.util.ArrayList;

class ArrayListValueChanger extends AbstractCollectionFieldValueChanger<ArrayList<?>> {

    @Override
    protected ArrayList<?> increaseValue(final ArrayList<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : CollectionUtils.asList(new Object());
    }
}
