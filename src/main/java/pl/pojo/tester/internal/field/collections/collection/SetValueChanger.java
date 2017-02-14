package pl.pojo.tester.internal.field.collections.collection;

import pl.pojo.tester.internal.utils.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

class SetValueChanger extends AbstractCollectionFieldValueChanger<Set<?>> {

    @Override
    protected Set<?> increaseValue(final Set<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new HashSet<>(CollectionUtils.asList(new Object()));
    }
}
