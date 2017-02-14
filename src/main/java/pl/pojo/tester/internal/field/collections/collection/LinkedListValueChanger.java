package pl.pojo.tester.internal.field.collections.collection;

import pl.pojo.tester.internal.utils.CollectionUtils;

import java.util.LinkedList;


class LinkedListValueChanger extends AbstractCollectionFieldValueChanger<LinkedList<?>> {


    @Override
    protected LinkedList<?> increaseValue(final LinkedList<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new LinkedList<>(CollectionUtils.asList(new Object()));
    }
}
