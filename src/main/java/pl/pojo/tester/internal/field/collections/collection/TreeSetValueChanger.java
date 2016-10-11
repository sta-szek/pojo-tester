package pl.pojo.tester.internal.field.collections.collection;

import java.util.TreeSet;

class TreeSetValueChanger extends AbstractCollectionFieldValueChanger<TreeSet<?>> {

    @Override
    protected TreeSet<?> increaseValue(final TreeSet<?> value, final Class<?> type) {
        return value != null
               ? null
               : new TreeSet<>();
    }
}
