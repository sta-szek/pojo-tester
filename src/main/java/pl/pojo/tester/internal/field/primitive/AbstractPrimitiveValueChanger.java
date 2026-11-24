package pl.pojo.tester.internal.field.primitive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.utils.CollectionUtils;
import pl.pojo.tester.internal.utils.FieldUtils;

import java.util.List;

public abstract class AbstractPrimitiveValueChanger<T> extends AbstractFieldValueChanger<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPrimitiveValueChanger.class);
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
    protected T increaseValue(final T value, final Class<?> type) {
        return value == null ? nullReplacement() : increase(value);
    }

    protected abstract T increase(T value);

    /**
     * Value used when a boxed field is {@code null}. It must be non-null so the changed instance
     * differs from the one that still holds {@code null}.
     */
    protected abstract T nullReplacement();


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
            LOGGER.debug("NoSuchFieldException:", e);
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
            LOGGER.debug("NoSuchFieldException:", e);
            return false;
        }
    }
}
