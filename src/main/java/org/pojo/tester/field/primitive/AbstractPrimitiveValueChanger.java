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
        return (isPrimitive(field) && isCompatibleWithPrimitive(field))
               || (isWrappedPrimitive(field) && isCompatibleWithWrappedPrimitive(field));
    }

    private boolean isPrimitive(final Field field) {
        return field.getType()
                    .isPrimitive();
    }

    private boolean isCompatibleWithPrimitive(final Field field) {
        try {
            return getGenericTypeClass().getField("TYPE")
                                        .get(null)
                                        .equals(field.getType());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    private boolean isWrappedPrimitive(final Field field) {
        final Class<?> clazz = field.getType();
        return clazz == Byte.class
               || clazz == Short.class
               || clazz == Integer.class
               || clazz == Long.class
               || clazz == Float.class
               || clazz == Double.class
               || clazz == Boolean.class
               || clazz == Character.class;
    }

    private boolean isCompatibleWithWrappedPrimitive(final Field field) {
        try {
            final Object fieldPrimitiveType = field.getType()
                                                   .getField("TYPE")
                                                   .get(null);
            return getGenericTypeClass().getField("TYPE")
                                        .get(null)
                                        .equals(fieldPrimitiveType);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    private Class<T> getGenericTypeClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
