package pl.pojo.tester.api;

import java.lang.reflect.Field;

/**
 * Exception is thrown when class has no getter for field.
 *
 * @author Piotr Joński
 * @since 0.1.0
 */
public class GetterNotFoundException extends RuntimeException {

    /**
     * Instantiates exception.
     *
     * @param clazz class declaring that field
     * @param field field, for which getter was not found
     */
    public GetterNotFoundException(final Class<?> clazz, final Field field) {
        super(String.format("Class %s has no getter for field %s", clazz.getCanonicalName(), field));
    }
}
