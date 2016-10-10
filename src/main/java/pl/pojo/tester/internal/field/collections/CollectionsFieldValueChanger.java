package pl.pojo.tester.internal.field.collections;


import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.field.collections.collection.AbstractCollectionFieldValueChanger;
import pl.pojo.tester.internal.field.collections.iterators.AbstractIteratorsFieldValueChanger;
import pl.pojo.tester.internal.field.collections.map.AbstractMapFieldValueChanger;

public final class CollectionsFieldValueChanger {

    public static final AbstractFieldValueChanger INSTANCE = new ArrayValueChanger()
            .attachNext(new StreamValueChanger())
            .attachNext(AbstractCollectionFieldValueChanger.INSTANCE)
            .attachNext(AbstractMapFieldValueChanger.INSTANCE)
            .attachNext(AbstractIteratorsFieldValueChanger.INSTANCE);

    private CollectionsFieldValueChanger() {}
}
