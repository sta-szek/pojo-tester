package pl.pojo.tester.api;

import java.lang.reflect.Field;

/**
 * Exception is thrown when class has no setter for field.
 *
 * @author Piotr Joński
 * @since 0.1.0
 */
public class SetterNotFoundException extends RuntimeException {

    /**
     * Instantiates exception.
     *
     * @param clazz class declaring that field
     * @param field field, for which setter was not found
     */
    public SetterNotFoundException(final Class<?> clazz, final Field field) {
        super(String.format("Class %s has no setter for field %s", clazz.getCanonicalName(), field));
    }
}
