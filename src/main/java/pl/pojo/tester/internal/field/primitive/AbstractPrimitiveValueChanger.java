package pl.pojo.tester.internal.field.primitive;

import com.google.common.collect.Lists;
import java.util.List;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

public abstract class AbstractPrimitiveValueChanger<T> extends AbstractFieldValueChanger<T> {

    public static final AbstractFieldValueChanger INSTANCE = new BooleanValueChanger().attachNext(new ByteValueChanger())
                                                                                      .attachNext(new CharacterValueChanger())
                                                                                      .attachNext(new DoubleValueChanger())
                                                                                      .attachNext(new IntegerValueChanger())
                                                                                      .attachNext(new LongValueChanger())
                                                                                      .attachNext(new ShortValueChanger())
                                                                                      .attachNext(new FloatValueChanger());

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
            return getGenericTypeClass().getField(FIELD_WITH_PRIMITIVE_CLASS_REFERENCE)
                                        .get(null)
                                        .equals(type);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    private boolean isWrappedPrimitive(final Class<?> type) {
        return PRIMITIVE_CLASSES.contains(type);
    }

    private boolean isCompatibleWithWrappedPrimitive(final Class<?> type) {
        try {
            final Object fieldPrimitiveType = type.getField(FIELD_WITH_PRIMITIVE_CLASS_REFERENCE)
                                                  .get(null);
            return getGenericTypeClass().getField(FIELD_WITH_PRIMITIVE_CLASS_REFERENCE)
                                        .get(null)
                                        .equals(fieldPrimitiveType);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }
}
