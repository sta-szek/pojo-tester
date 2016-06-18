package org.pojo.tester.field;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pojo.tester.field.collections.CollectionFieldValueChanger;
import org.pojo.tester.field.primitive.AbstractPrimitiveValueChanger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultFieldValueChanger {

    public static final AbstractFieldValueChanger INSTANCE = new EnumValueChanger().register(AbstractPrimitiveValueChanger.INSTANCE)
                                                                                   .register(CollectionFieldValueChanger.INSTANCE);
}
