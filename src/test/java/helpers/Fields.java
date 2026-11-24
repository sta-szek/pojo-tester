package helpers;

import java.lang.reflect.Field;

public final class Fields {

    private Fields() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInternalState(final Object target, final String fieldName) {
        try {
            return (T) declaredField(target, fieldName).get(target);
        } catch (final IllegalAccessException exception) {
            throw new IllegalStateException("Cannot read " + fieldName, exception);
        }
    }

    public static void setInternalState(final Object target, final String fieldName, final Object value) {
        try {
            declaredField(target, fieldName).set(target, value);
        } catch (final IllegalAccessException exception) {
            throw new IllegalStateException("Cannot write " + fieldName, exception);
        }
    }

    private static Field declaredField(final Object target, final String fieldName) {
        Class<?> type = target.getClass();
        while (type != null) {
            try {
                final Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (final NoSuchFieldException notDeclaredHere) {
                type = type.getSuperclass();
            }
        }
        throw new IllegalArgumentException("No field " + fieldName + " on " + target.getClass().getName());
    }
}
