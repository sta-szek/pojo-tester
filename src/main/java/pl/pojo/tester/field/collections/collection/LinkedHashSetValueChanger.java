package pl.pojo.tester.field.collections.collection;

import java.util.LinkedHashSet;

class LinkedHashSetValueChanger extends AbstractCollectionFieldValueChanger<LinkedHashSet<?>> {


    @Override
    protected LinkedHashSet<?> increaseValue(final LinkedHashSet<?> value, final Class<?> type) {
        return value != null
               ? null
               : new LinkedHashSet<>();
    }
}
