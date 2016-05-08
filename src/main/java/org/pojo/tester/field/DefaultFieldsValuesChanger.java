package org.pojo.tester.field;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pojo.tester.field.primitive.AbstractPrimitiveValueChanger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultFieldsValuesChanger {

    public static final AbstractFieldsValuesChanger INSTANCE = AbstractPrimitiveValueChanger.getInstance()
                                                                                            .register(new EnumValueChanger());
}
