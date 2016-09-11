package pl.pojo.tester.field.collections;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pojo.tester.field.AbstractFieldValueChanger;
import pl.pojo.tester.field.collections.collection.AbstractCollectionFieldValueChanger;
import pl.pojo.tester.field.collections.iterators.AbstractIteratorsFieldValueChanger;
import pl.pojo.tester.field.collections.map.AbstractMapFieldValueChanger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionsFieldValueChanger {
    public static final AbstractFieldValueChanger INSTANCE = new ArrayValueChanger().attachNext(new StreamValueChanger())
                                                                                    .attachNext(AbstractCollectionFieldValueChanger.INSTANCE)
                                                                                    .attachNext(AbstractMapFieldValueChanger.INSTANCE)
                                                                                    .attachNext(AbstractIteratorsFieldValueChanger.INSTANCE);
}
