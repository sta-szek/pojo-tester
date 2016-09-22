package pl.pojo.tester.internal.assertion.equals;


class ReflexiveEqualsAssertionError extends EqualsAssertionError {

    private static final String CONSTRAINT_REFLEXIVE = "The equals method should return true if object is comparing to itself.\n"
                                                       + "Current implementation returns false.\n"
                                                       + "Object:\n"
                                                       + "%s\n"
                                                       + "should be equal to:\n"
                                                       + "%s";
    private final Object testedObject;

    ReflexiveEqualsAssertionError(final Class<?> testedCass, final Object testedObject) {
        super(testedCass);
        this.testedObject = testedObject;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_REFLEXIVE, testedObject, testedObject);
    }
}
