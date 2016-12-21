package pl.pojo.tester.internal.field.collections.collection;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedHashSet;

class LinkedHashSetValueChanger extends AbstractCollectionFieldValueChanger<LinkedHashSet<?>> {


    @Override
    protected LinkedHashSet<?> increaseValue(final LinkedHashSet<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new LinkedHashSet<>(Lists.newArrayList(new Object()));
    }
}
