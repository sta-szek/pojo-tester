package pl.pojo.tester.internal.field.collections.collection;

import pl.pojo.tester.internal.utils.CollectionUtils;

import java.util.LinkedHashSet;

class LinkedHashSetValueChanger extends AbstractCollectionFieldValueChanger<LinkedHashSet<?>> {


    @Override
    protected LinkedHashSet<?> increaseValue(final LinkedHashSet<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new LinkedHashSet<>(CollectionUtils.asList(new Object()));
    }
}
