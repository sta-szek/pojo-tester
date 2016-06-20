package org.pojo.tester.field.collections.collection;

import java.util.HashSet;

class HashSetValueChanger extends AbstractCollectionFieldValueChanger<HashSet<?>> {


    @Override
    protected HashSet<?> increaseValue(final HashSet<?> value, final Class<?> type) {
        return value != null
               ? null
               : new HashSet<>();
    }
}
