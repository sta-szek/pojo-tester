package pl.pojo.tester.internal.assertion.equals;


class OtherTypeEqualsAssertionError extends EqualsAssertionError {

    private static final String CONSTRAINT_OTHER_TYPE = "The equals method should return false if object is comparing to object with "
                                                        + "different type.\n"
                                                        + "Current implementation returns true.\n"
                                                        + "Object:\n"
                                                        + "%s\n"
                                                        + "should not be equal to:\n"
                                                        + "%s";
    private final Object testedObject;
    private final Object otherObject;

    OtherTypeEqualsAssertionError(final Class<?> testedCass, final Object testedObject, final Object otherObject) {
        super(testedCass);
        this.testedObject = testedObject;
        this.otherObject = otherObject;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_OTHER_TYPE, testedObject, otherObject);
    }
}
