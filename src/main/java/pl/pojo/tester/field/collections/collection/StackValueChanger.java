package pl.pojo.tester.field.collections.collection;

import java.util.Stack;

class StackValueChanger extends AbstractCollectionFieldValueChanger<Stack<?>> {

    @Override
    protected Stack<?> increaseValue(final Stack<?> value, final Class<?> type) {
        return value != null
               ? null
               : new Stack<>();
    }
}
