package pl.pojo.tester.assertion.equals;


class NotEqualEqualsAssertionError extends EqualsAssertionError {

    private static final String CONSTRAINT_NOT_EQUAL = "The equals method should return false if objects should not be equal.\n"
                                                       + "Current implementation returns true.\n"
                                                       + "Object:\n"
                                                       + "%s\n"
                                                       + "should not be equal to:\n"
                                                       + "%s";
    private final Object testedObject;
    private final Object otherObject;

    NotEqualEqualsAssertionError(final Class<?> testedCass, final Object testedObject, final Object otherObject) {
        super(testedCass);
        this.testedObject = testedObject;
        this.otherObject = otherObject;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_NOT_EQUAL, testedObject, otherObject);
    }
}
