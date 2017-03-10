package pl.pojo.tester.internal.field.collections.collection;


import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.util.Collection;

public abstract class AbstractCollectionFieldValueChanger<T extends Collection> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new ArrayListValueChanger().attachNext(new DequeValueChanger())
                                                                                        .attachNext(new HashSetValueChanger())
                                                                                        .attachNext(new LinkedHashSetValueChanger())
                                                                                        .attachNext(new LinkedListValueChanger())
                                                                                        .attachNext(new ListValueChanger())
                                                                                        .attachNext(new QueueValueChanger())
                                                                                        .attachNext(new SetValueChanger())
                                                                                        .attachNext(new SortedSetValueChanger())
                                                                                        .attachNext(new StackValueChanger())
                                                                                        .attachNext(new TreeSetValueChanger())
                                                                                        .attachNext(new VectorValueChanger());

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.isAssignableFrom(getGenericTypeClass());
    }

}
