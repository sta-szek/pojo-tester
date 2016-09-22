package pl.pojo.tester.internal.assertion;


public abstract class AssertionError extends RuntimeException {

    protected static Class<?> testedCass;


    public AssertionError(final Class<?> testedCass) {
        AssertionError.testedCass = testedCass;
        setStackTrace(new StackTraceElement[]{});
    }

    @Override
    public String getMessage() {
        return "\n\n\n" + getErrorPrefix() + "\n" + getDetailedMessage();
    }

    protected abstract String getErrorPrefix();

    protected abstract String getDetailedMessage();
}
