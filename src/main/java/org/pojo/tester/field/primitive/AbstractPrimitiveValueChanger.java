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


    public static AbstractFieldsValuesChanger getInstance() {
        return INSTANCE;
    }

    @Override
    protected boolean canChange(final Field field) {
        return isPrimitive(field) && isCompatibleType(field);
    }

    private boolean isPrimitive(final Field field) {
        return field.getType()
                    .isPrimitive();
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

    private Class<T> getGenericTypeClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
