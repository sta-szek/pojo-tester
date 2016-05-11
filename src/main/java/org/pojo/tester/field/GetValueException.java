package org.pojo.tester.field;


public class GetValueException extends RuntimeException {

    public GetValueException(final String fieldName, final Class<?> clazz, final Exception cause) {
        super(createMessage(fieldName, clazz), cause);
    }

    private static String createMessage(final String fieldName, final Class<?> clazz) {
        return "Unable to get value for field '" + fieldName + "' in class '" + clazz.getClass() + "'.";
    }
}
