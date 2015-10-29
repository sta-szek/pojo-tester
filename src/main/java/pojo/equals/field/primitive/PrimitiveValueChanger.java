package pojo.equals.field.primitive;

import pojo.equals.field.FieldsValuesChanger;

import java.lang.reflect.Field;


public abstract class PrimitiveValueChanger<T> extends FieldsValuesChanger<T> {

    private static final Class[] primitiveValueChangers = {BooleanValueChanger.class,
                                                           ByteValueChanger.class,
                                                           CharacterValueChanger.class,
                                                           DoubleValueChanger.class,
                                                           IntegerValueChanger.class,
                                                           LongValueChanger.class,
                                                           ShortValueChanger.class};

    public static FieldsValuesChanger instance() throws IllegalAccessException, InstantiationException {
        FieldsValuesChanger firstObject = (FieldsValuesChanger) primitiveValueChangers[0].newInstance();
        for (int i = 1; i < primitiveValueChangers.length; i++) {
            FieldsValuesChanger next = (FieldsValuesChanger) primitiveValueChangers[i].newInstance();
            firstObject.register(next);
        }
        return firstObject;
    }

    @Override
    protected boolean canChange(Field field) {
        return super.canChange(field) && isCompatibleType(field);
    }

    private boolean isCompatibleType(Field field) {
        try {
            return getGenericTypeClass().getField("TYPE")
                                        .get(null)
                                        .equals(field.getType());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }
}
