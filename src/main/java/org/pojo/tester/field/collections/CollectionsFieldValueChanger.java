package org.pojo.tester.field.collections;


import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.field.collections.collection.AbstractCollectionFieldValueChanger;
import org.pojo.tester.field.collections.map.AbstractMapFieldValueChanger;

public class CollectionsFieldValueChanger {
    public static final AbstractFieldValueChanger INSTANCE = new ArrayValueChanger().register(new StreamValueChanger())
                                                                                    .register(AbstractCollectionFieldValueChanger.INSTANCE)
                                                                                    .register(AbstractMapFieldValueChanger.INSTANCE);
}
