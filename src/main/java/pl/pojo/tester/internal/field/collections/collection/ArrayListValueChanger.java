package pl.pojo.tester.internal.field.collections.collection;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;

class ArrayListValueChanger extends AbstractCollectionFieldValueChanger<ArrayList<?>> {

    @Override
    protected ArrayList<?> increaseValue(final ArrayList<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : Lists.newArrayList(new Object());
    }
}
