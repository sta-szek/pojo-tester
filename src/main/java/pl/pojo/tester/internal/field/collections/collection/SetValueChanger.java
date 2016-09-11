package pl.pojo.tester.internal.field.collections.collection;

import java.util.Collections;
import java.util.Set;

class SetValueChanger extends AbstractCollectionFieldValueChanger<Set<?>> {

    @Override
    protected Set<?> increaseValue(final Set<?> value, final Class<?> type) {
        return value != null
               ? null
               : Collections.EMPTY_SET;
    }
}
