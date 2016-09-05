package org.pojo.tester;


public class GetOrSetValueException extends RuntimeException {

    public GetOrSetValueException(final String fieldName, final Class<?> clazz, final Exception cause) {
        super(createMessage(fieldName, clazz), cause);
    }

    private static String createMessage(final String fieldName, final Class<?> clazz) {
        return "Unable to get or set value for field '" + fieldName + "' in class '" + clazz + "'.";
    }
}
