package pl.pojo.tester.internal.field.collections.collection;

import java.util.LinkedList;


class LinkedListValueChanger extends AbstractCollectionFieldValueChanger<LinkedList<?>> {


    @Override
    protected LinkedList<?> increaseValue(final LinkedList<?> value, final Class<?> type) {
        return value != null
               ? null
               : new LinkedList<>();
    }
}
