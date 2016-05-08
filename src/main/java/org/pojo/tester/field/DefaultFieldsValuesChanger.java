package org.pojo.tester.field;


import org.pojo.tester.field.primitive.AbstractPrimitiveValueChanger;

public class DefaultFieldsValuesChanger {

    public static final AbstractFieldsValuesChanger INSTANCE = AbstractPrimitiveValueChanger.getInstance()
                                                                                            .register(new EnumValueChanger());
}
