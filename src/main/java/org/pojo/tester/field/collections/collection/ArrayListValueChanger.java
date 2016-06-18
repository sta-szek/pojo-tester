package org.pojo.tester.field.collections.collection;

import java.util.ArrayList;

class ArrayListValueChanger extends AbstractCollectionFieldValueChanger<ArrayList<?>> {

    @Override
    protected ArrayList<?> increaseValue(final ArrayList<?> value, final Class<?> type) {
        return value != null
               ? null
               : new ArrayList<>();
    }
}
