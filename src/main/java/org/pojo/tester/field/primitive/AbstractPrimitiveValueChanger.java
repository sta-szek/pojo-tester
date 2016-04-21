package org.pojo.tester.field.primitive;

import org.pojo.tester.field.AbstractFieldsValuesChanger;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;


public abstract class AbstractPrimitiveValueChanger<T> extends AbstractFieldsValuesChanger<T> {

    private static final AbstractFieldsValuesChanger INSTANCE = new BooleanValueChanger().register(new BooleanValueChanger())
                                                                                         .register(new ByteValueChanger())
                                                                                         .register(new CharacterValueChanger())
                                                                                         .register(new DoubleValueChanger())
                                                                                         .register(new IntegerValueChanger())
                                                                                         .register(new LongValueChanger())
                                                                                         .register(new ShortValueChanger())
                                                                                         .register(new FloatValueChanger());


    public static AbstractFieldsValuesChanger getInstance() throws IllegalAccessException, InstantiationException {
        return INSTANCE;
    }

    @Override
    protected boolean canChange(final Field field) {
        return super.canChange(field) && isCompatibleType(field);
    }

    Class getGenericTypeClass() {
        return (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private boolean isCompatibleType(final Field field) {
        try {
            return getGenericTypeClass().getField("TYPE")
                                        .get(null)
                                        .equals(field.getType());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }
}
