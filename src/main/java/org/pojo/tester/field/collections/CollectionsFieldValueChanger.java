package org.pojo.tester.field.collections;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.field.collections.collection.AbstractCollectionFieldValueChanger;
import org.pojo.tester.field.collections.iterators.AbstractIteratorsFieldValueChanger;
import org.pojo.tester.field.collections.map.AbstractMapFieldValueChanger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionsFieldValueChanger {
    public static final AbstractFieldValueChanger INSTANCE = new ArrayValueChanger().attachNext(new StreamValueChanger())
                                                                                    .attachNext(AbstractCollectionFieldValueChanger.INSTANCE)
                                                                                    .attachNext(AbstractMapFieldValueChanger.INSTANCE)
                                                                                    .attachNext(AbstractIteratorsFieldValueChanger.INSTANCE);
}
