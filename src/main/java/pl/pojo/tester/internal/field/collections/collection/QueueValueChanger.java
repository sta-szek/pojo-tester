package pl.pojo.tester.internal.field.collections.collection;

import pl.pojo.tester.internal.utils.CollectionUtils;

import java.util.LinkedList;
import java.util.Queue;

class QueueValueChanger extends AbstractCollectionFieldValueChanger<Queue<?>> {

    @Override
    protected Queue<?> increaseValue(final Queue<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new LinkedList<>(CollectionUtils.asList(new Object()));
    }
}
