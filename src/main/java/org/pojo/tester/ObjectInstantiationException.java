package org.pojo.tester;


public class ObjectInstantiationException extends RuntimeException {

    public ObjectInstantiationException(final Class<?> clazz, final ReflectiveOperationException cause) {
        super(createMessage(clazz), cause);
    }

    private static String createMessage(final Class<?> clazz) {return "Unable to create object for class: " + clazz;}
}
