package pl.pojo.tester.internal.field.collections.collection;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;

class HashSetValueChanger extends AbstractCollectionFieldValueChanger<HashSet<?>> {


    @Override
    protected HashSet<?> increaseValue(final HashSet<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new HashSet<>(Lists.newArrayList(new Object()));
    }
}
