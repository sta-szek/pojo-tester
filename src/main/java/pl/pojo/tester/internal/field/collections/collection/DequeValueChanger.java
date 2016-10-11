package pl.pojo.tester.internal.field.collections.collection;

import java.util.Deque;
import java.util.LinkedList;

class DequeValueChanger extends AbstractCollectionFieldValueChanger<Deque<?>> {

    @Override
    protected Deque<?> increaseValue(final Deque<?> value, final Class<?> type) {
        return value != null
               ? null
               : new LinkedList<>();
    }
}
