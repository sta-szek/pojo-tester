package pl.pojo.tester.internal.preconditions;

public class NullParameterException extends RuntimeException {

    public NullParameterException(final String parameterName) {
        super(createMessage(parameterName));
    }

    private static String createMessage(final String parameterName) {
        return String.format("Parameter '%s' has null value.", parameterName);
    }
}
