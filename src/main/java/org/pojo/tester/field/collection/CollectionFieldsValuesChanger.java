package org.pojo.tester.field.collection;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pojo.tester.field.AbstractFieldsValuesChanger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionFieldsValuesChanger {

    public static final AbstractFieldsValuesChanger INSTANCE = new ArrayValueChanger().register(new StreamValueChanger());
}
