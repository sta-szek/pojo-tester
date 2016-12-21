package pl.pojo.tester.internal.field.collections.collection;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Deque;
import java.util.LinkedList;

class DequeValueChanger extends AbstractCollectionFieldValueChanger<Deque<?>> {

    @Override
    protected Deque<?> increaseValue(final Deque<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new LinkedList<>(Lists.newArrayList(new Object()));
    }
}
