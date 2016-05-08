package org.pojo.tester.field.primitive;

import com.google.common.collect.Lists;
import org.pojo.tester.field.AbstractFieldsValuesChanger;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractPrimitiveValueChanger<T> extends AbstractFieldsValuesChanger<T> {

    private static final AbstractFieldsValuesChanger INSTANCE = new BooleanValueChanger().register(new BooleanValueChanger())
                                                                                         .register(new ByteValueChanger())
                                                                                         .register(new CharacterValueChanger())
                                                                                         .register(new DoubleValueChanger())
                                                                                         .register(new IntegerValueChanger())
                                                                                         .register(new LongValueChanger())
                                                                                         .register(new ShortValueChanger())
                                                                                         .register(new FloatValueChanger());

    private static final List<Class<?>> PRIMITIVE_CLASSES = Lists.newArrayList(Float.class,
                                                                               Integer.class,
                                                                               Long.class,
                                                                               Float.class,
                                                                               Double.class,
                                                                               Byte.class,
                                                                               Short.class,
                                                                               Boolean.class,
                                                                               Character.class);
    private static final String FIELD_WITH_PRIMITIVE_CLASS_REFERENCE = "TYPE";


    public static AbstractFieldsValuesChanger getInstance() {
        return INSTANCE;
    }

    @Override
    protected boolean canChange(final Field field) {
        return isPrimitive(field) && isCompatibleWithPrimitive(field)
               || isWrappedPrimitive(field) && isCompatibleWithWrappedPrimitive(field);
    }

    @Override
    protected T increaseValue(final T value, final Class<?> type) {
        return increaseValue(value);
    }

    protected abstract T increaseValue(final T value);

    private boolean isPrimitive(final Field field) {
        return field.getType()
                    .isPrimitive();
    }

    private boolean isCompatibleWithPrimitive(final Field field) {
        try {
            return getGenericTypeClass().getField(FIELD_WITH_PRIMITIVE_CLASS_REFERENCE)
                                        .get(null)
                                        .equals(field.getType());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    private boolean isWrappedPrimitive(final Field field) {
        final Class<?> clazz = field.getType();
        return PRIMITIVE_CLASSES.contains(clazz);
    }

    private boolean isCompatibleWithWrappedPrimitive(final Field field) {
        try {
            final Object fieldPrimitiveType = field.getType()
                                                   .getField(FIELD_WITH_PRIMITIVE_CLASS_REFERENCE)
                                                   .get(null);
            return getGenericTypeClass().getField(FIELD_WITH_PRIMITIVE_CLASS_REFERENCE)
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
