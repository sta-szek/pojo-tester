package org.pojo.tester.assertion.equals;


public class ReflexiveEqualsAssertionError extends EqualsAssertionError {

    private static final String CONSTRAINT_REFLEXIVE = "The equals method should return true if object is comparing to itself."
                                                       + "Current implementation returns false.\n"
                                                       + "Object:\n"
                                                       + "%s\n"
                                                       + "should be equal to:\n"
                                                       + "%s\n";
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
