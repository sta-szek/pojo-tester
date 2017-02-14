package pl.pojo.tester.internal.field.collections.collection;

import pl.pojo.tester.internal.utils.CollectionUtils;

import java.util.Vector;

class VectorValueChanger extends AbstractCollectionFieldValueChanger<Vector<?>> {

    @Override
    protected Vector<?> increaseValue(final Vector<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new Vector<>(CollectionUtils.asList(new Object()));
    }
}
