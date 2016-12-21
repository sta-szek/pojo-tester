package pl.pojo.tester.internal.field.collections.collection;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

class ListValueChanger extends AbstractCollectionFieldValueChanger<List<?>> {

    @Override
    protected List<?> increaseValue(final List<?> value, final Class<?> type) {
        return CollectionUtils.isNotEmpty(value)
               ? null
               : Lists.newArrayList(new Object());
    }
}
