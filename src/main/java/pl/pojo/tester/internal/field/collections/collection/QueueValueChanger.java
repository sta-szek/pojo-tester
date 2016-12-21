package pl.pojo.tester.internal.field.collections.collection;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedList;
import java.util.Queue;

class QueueValueChanger extends AbstractCollectionFieldValueChanger<Queue<?>> {

    @Override
    protected Queue<?> increaseValue(final Queue<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new LinkedList<>(Lists.newArrayList(new Object()));
    }
}
