package org.pojo.tester.field.collection;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pojo.tester.field.AbstractFieldValueChanger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionFieldValueChanger {

    public static final AbstractFieldValueChanger INSTANCE = new ArrayValueChanger().register(new StreamValueChanger());
}
