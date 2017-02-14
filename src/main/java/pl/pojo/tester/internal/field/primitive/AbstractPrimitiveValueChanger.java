package pl.pojo.tester.internal.field.primitive;

import java.util.List;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.utils.CollectionUtils;
import pl.pojo.tester.internal.utils.FieldUtils;

public abstract class AbstractPrimitiveValueChanger<T> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new BooleanValueChanger().attachNext(new ByteValueChanger())
                                                                                      .attachNext(new CharacterValueChanger())
                                                                                      .attachNext(new DoubleValueChanger())
                                                                                      .attachNext(new IntegerValueChanger())
                                                                                      .attachNext(new LongValueChanger())
                                                                                      .attachNext(new ShortValueChanger())
                                                                                      .attachNext(new FloatValueChanger());

    private static final List<Class<?>> PRIMITIVE_CLASSES = CollectionUtils.asList(Float.class,
                                                                                   Integer.class,
                                                                                   Long.class,
                                                                                   Float.class,
                                                                                   Double.class,
                                                                                   Byte.class,
                                                                                   Short.class,
                                                                                   Boolean.class,
                                                                                   Character.class);
    private static final String FIELD_WITH_PRIMITIVE_CLASS_REFERENCE = "TYPE";

    @Override
    public boolean areDifferentValues(final T sourceValue, final T targetValue) {
        if (sourceValue == targetValue) {
            return false;
        }
        if (sourceValue == null || targetValue == null) {
            return true;
        } else {
            return areDifferent(sourceValue, targetValue);
        }
    }

    @Override
    protected T increaseValue(final T value, final Class<?> type) {
        return increase(value);
    }

    protected abstract T increase(T value);

    protected abstract boolean areDifferent(T sourceValue, T targetValue);

    @Override
    protected boolean canChange(final Class<?> type) {
        return isPrimitive(type) && isCompatibleWithPrimitive(type)
               || isWrappedPrimitive(type) && isCompatibleWithWrappedPrimitive(type);
    }

    private boolean isPrimitive(final Class<?> type) {
        return type.isPrimitive();
    }

    private boolean isCompatibleWithPrimitive(final Class<?> type) {
        try {
            return FieldUtils.getValue(null, getGenericTypeClass().getField(FIELD_WITH_PRIMITIVE_CLASS_REFERENCE))
                             .equals(type);
        } catch (final NoSuchFieldException e) {
            return false;
        }
    }

    private boolean isWrappedPrimitive(final Class<?> type) {
        return PRIMITIVE_CLASSES.contains(type);
    }

    private boolean isCompatibleWithWrappedPrimitive(final Class<?> type) {
        try {
            final Object fieldPrimitiveType = FieldUtils.getValue(null,
                                                                  type.getField(FIELD_WITH_PRIMITIVE_CLASS_REFERENCE));
            return FieldUtils.getValue(null, getGenericTypeClass().getField(FIELD_WITH_PRIMITIVE_CLASS_REFERENCE))
                             .equals(fieldPrimitiveType);
        } catch (final NoSuchFieldException e) {
            return false;
        }
    }
}
