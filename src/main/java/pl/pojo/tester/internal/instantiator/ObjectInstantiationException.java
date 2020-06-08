package pl.pojo.tester.internal.instantiator;


import java.util.Arrays;

public class ObjectInstantiationException extends RuntimeException {

    ObjectInstantiationException(final Class<?> clazz, final String message, final Throwable cause) {
        super(createMessage(clazz) + " " + message, cause);
    }

    ObjectInstantiationException(final Class<?> clazz, final String message) {
        this(clazz, message, null);
    }

    public ObjectInstantiationException(final Class<?> clazz,
                                        final String message,
                                        final Class<?>[] parameterTypes,
                                        final Object[] parameters,
                                        final Throwable cause) {
        this(clazz, message + " " + createMessage(parameterTypes, parameters), cause);
    }

    private static String createMessage(final Class<?> clazz) {
        return "Unable to create object for class: " + clazz;
    }

    private static String createMessage(final Class<?>[] parameterTypes, final Object[] parameters) {
        return "Parameter types are "
               + Arrays.toString(parameterTypes)
               + " and parameters are "
               + Arrays.toString(parameters);
    }


}
