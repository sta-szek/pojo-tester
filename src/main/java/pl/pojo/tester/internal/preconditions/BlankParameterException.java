package pl.pojo.tester.internal.preconditions;

public class BlankParameterException extends RuntimeException {
    public BlankParameterException(final String parameterName, final String parameterValue) {
        super(createMessage(parameterName, parameterValue));
    }

    private static String createMessage(final String parameterName, final String parameterValue) {
        return String.format("Parameter '%s' is blank. It's value is '%s'", parameterName, parameterValue);
    }
}
