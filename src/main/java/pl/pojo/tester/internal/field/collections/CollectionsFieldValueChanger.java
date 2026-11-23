package pl.pojo.tester.internal.field.collections;


import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.field.collections.collection.CollectionValueChangers;
import pl.pojo.tester.internal.field.collections.iterators.IteratorsValueChangers;
import pl.pojo.tester.internal.field.collections.map.MapValueChangers;

public final class CollectionsFieldValueChanger {

    public static final AbstractFieldValueChanger INSTANCE = new ArrayValueChanger()
            .attachNext(new StreamValueChanger())
            .attachNext(CollectionValueChangers.INSTANCE)
            .attachNext(MapValueChangers.INSTANCE)
            .attachNext(IteratorsValueChangers.INSTANCE);

    private CollectionsFieldValueChanger() {}
}
