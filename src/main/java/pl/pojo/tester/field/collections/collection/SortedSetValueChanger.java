package pl.pojo.tester.field.collections.collection;

import java.util.SortedSet;
import java.util.TreeSet;

class SortedSetValueChanger extends AbstractCollectionFieldValueChanger<SortedSet<?>> {

    @Override
    protected SortedSet<?> increaseValue(final SortedSet<?> value, final Class<?> type) {
        return value != null
               ? null
               : new TreeSet<>();
    }
}
