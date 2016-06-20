package org.pojo.tester.field.collections.collection;

import java.util.Vector;

class VectorValueChanger extends AbstractCollectionFieldValueChanger<Vector<?>> {

    @Override
    protected Vector<?> increaseValue(final Vector<?> value, final Class<?> type) {
        return value != null
               ? null
               : new Vector<>();
    }
}
