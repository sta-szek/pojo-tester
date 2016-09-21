package pl.pojo.tester.internal.instantiator;


class ObjectInstantiationException extends RuntimeException {

    ObjectInstantiationException(final Class<?> clazz, final String message, final Throwable cause) {
        super(createMessage(clazz) + " " + message, cause);
    }

    ObjectInstantiationException(final Class<?> clazz, final String message) {
        this(clazz, message, null);
    }

    private static String createMessage(final Class<?> clazz) {
        return "Unable to create object for class: " + clazz;
    }
}
