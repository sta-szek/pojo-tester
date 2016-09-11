package pl.pojo.tester.assertion.equals;


class NullEqualsAssertionError extends EqualsAssertionError {

    private static final String CONSTRAINT_NULL = "The equals method should return false if object is comparing to null.\n"
                                                  + "Current implementation returns true.";

    NullEqualsAssertionError(final Class<?> testedCass) {
        super(testedCass);
    }

    @Override
    protected String getDetailedMessage() {
        return CONSTRAINT_NULL;
    }
}
