package pl.pojo.tester.internal.assertion;


public abstract class AssertionError extends RuntimeException {

    protected final Class<?> testedCass;

    public AssertionError(final Class<?> testedCass) {
        this.testedCass = testedCass;
        setStackTrace(new StackTraceElement[]{});
    }

    @Override
    public String getMessage() {
        return "\n\n\n" + getErrorPrefix() + "\n" + getDetailedMessage();
    }

    protected abstract String getErrorPrefix();

    protected abstract String getDetailedMessage();
}
