package org.pojo.tester.field.collections.collection;

import java.util.Collections;
import java.util.List;

class ListValueChanger extends AbstractCollectionFieldValueChanger<List<?>> {

    @Override
    protected List<?> increaseValue(final List<?> value, final Class<?> type) {
        return value != null
               ? null
               : Collections.EMPTY_LIST;
    }
}
