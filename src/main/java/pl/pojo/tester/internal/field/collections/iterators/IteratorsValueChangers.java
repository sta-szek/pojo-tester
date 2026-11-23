package pl.pojo.tester.internal.field.collections.iterators;


import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

public final class IteratorsValueChangers {

    public static final AbstractFieldValueChanger INSTANCE =
            new IteratorValueChanger().attachNext(new IterableValueChanger());

    private IteratorsValueChangers() {
    }
}
