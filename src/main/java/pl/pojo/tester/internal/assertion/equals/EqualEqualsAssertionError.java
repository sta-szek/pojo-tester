package pl.pojo.tester.internal.assertion.equals;


class EqualEqualsAssertionError extends EqualsAssertionError {

    private static final String CONSTRAINT_NOT_EQUAL =
            "The equals method should return true if objects should be equal.\n"
            + "Current implementation returns false.\n"
            + "Object:\n"
            + "%s\n"
            + "should be equal to:\n"
            + "%s";
    private final Object testedObject;
    private final Object otherObject;

    EqualEqualsAssertionError(final Class<?> testedCass, final Object testedObject, final Object otherObject) {
        super(testedCass);
        this.testedObject = testedObject;
        this.otherObject = otherObject;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_NOT_EQUAL, testedObject, otherObject);
    }
}
