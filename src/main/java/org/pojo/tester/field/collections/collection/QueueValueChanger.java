package org.pojo.tester.field.collections.collection;

import java.util.LinkedList;
import java.util.Queue;

class QueueValueChanger extends AbstractCollectionFieldValueChanger<Queue<?>> {

    @Override
    protected Queue<?> increaseValue(final Queue<?> value, final Class<?> type) {
        return value != null
               ? null
               : new LinkedList<>();
    }
}
