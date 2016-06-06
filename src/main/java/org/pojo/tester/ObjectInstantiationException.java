package org.pojo.tester;


public class ObjectInstantiationException extends RuntimeException {

    public ObjectInstantiationException(final Class<?> clazz, final Throwable cause) {
        super(createMessage(clazz), cause);
    }

    public ObjectInstantiationException(final Class<?> clazz, final String message) {
        super(createMessage(clazz) + " " + message);
    }

    private static String createMessage(final Class<?> clazz) {
        return "Unable to create object for class: " + clazz;
    }
}
