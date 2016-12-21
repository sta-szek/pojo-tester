package pl.pojo.tester.internal.field.collections.collection;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Vector;

class VectorValueChanger extends AbstractCollectionFieldValueChanger<Vector<?>> {

    @Override
    protected Vector<?> increaseValue(final Vector<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : new Vector<>(Lists.newArrayList(new Object()));
    }
}
