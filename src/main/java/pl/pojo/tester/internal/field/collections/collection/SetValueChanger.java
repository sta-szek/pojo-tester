package pl.pojo.tester.internal.field.collections.collection;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

class SetValueChanger extends AbstractCollectionFieldValueChanger<Set<?>> {

    @Override
    protected Set<?> increaseValue(final Set<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new HashSet<>(Lists.newArrayList(new Object()));
    }
}
