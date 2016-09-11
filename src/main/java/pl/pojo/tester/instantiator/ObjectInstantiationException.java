package pl.pojo.tester.instantiator;


class ObjectInstantiationException extends RuntimeException {

    ObjectInstantiationException(final Class<?> clazz, final Throwable cause) {
        this(clazz.getName(), cause);
    }

    ObjectInstantiationException(final String qualifiedClassName, final Throwable cause) {
        super(createMessage(qualifiedClassName), cause);
    }

    ObjectInstantiationException(final Class<?> clazz, final String message) {
        super(createMessage(clazz) + " " + message);
    }

    private static String createMessage(final String qualifiedClassName) {
        return "Unable to load class: " + qualifiedClassName;
    }

    private static String createMessage(final Class<?> clazz) {
        return "Unable to create object for class: " + clazz;
    }
}
