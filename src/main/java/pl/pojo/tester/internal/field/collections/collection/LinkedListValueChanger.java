package pl.pojo.tester.internal.field.collections.collection;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedList;


class LinkedListValueChanger extends AbstractCollectionFieldValueChanger<LinkedList<?>> {


    @Override
    protected LinkedList<?> increaseValue(final LinkedList<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new LinkedList<>(Lists.newArrayList(new Object()));
    }
}
