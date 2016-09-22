package pl.pojo.tester.internal.field.collections.collection;


import java.util.Arrays;
import java.util.Collection;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

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
    public boolean areDifferentValues(final T sourceValue, final T targetValue) {
        if (sourceValue == targetValue) {
            return false;
        }
        if (sourceValue == null || targetValue == null) {
            return true;
        } else {
            final Object[] sourceValuesArray = sourceValue.toArray();
            final Object[] targetValuesArray = targetValue.toArray();
            return !Arrays.deepEquals(sourceValuesArray, targetValuesArray);
        }
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.isAssignableFrom(getGenericTypeClass());
    }

}
