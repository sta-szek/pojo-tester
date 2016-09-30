package pl.pojo.tester.api;

/**
 * Exception is thrown when value of field cannot be changed or accessed.
 *
 * @author Piotr Joński
 * @since 0.1.0
 */
public class GetOrSetValueException extends RuntimeException {

    /**
     * Instantiates exception.
     *
     * @param fieldName field name, which cannot be changed or accessed
     * @param clazz     class declaring that field
     * @param cause     root cause of this exception
     */
    public GetOrSetValueException(final String fieldName, final Class<?> clazz, final Exception cause) {
        super(createMessage(fieldName, clazz, cause.getMessage()), cause);
    }

    private static String createMessage(final String fieldName, final Class<?> clazz, final String causeMessage) {
        return "Unable to get or set value for field '" + fieldName + "' in class '" + clazz + "'."
               + "\n Cause message: " + causeMessage;
    }
}
