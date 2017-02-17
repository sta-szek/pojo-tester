package pl.pojo.tester.internal.field.collections.collection;

import pl.pojo.tester.internal.utils.CollectionUtils;

import java.util.Deque;
import java.util.LinkedList;

class DequeValueChanger extends AbstractCollectionFieldValueChanger<Deque<?>> {

    @Override
    protected Deque<?> increaseValue(final Deque<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new LinkedList<>(CollectionUtils.asList(new Object()));
    }
}
