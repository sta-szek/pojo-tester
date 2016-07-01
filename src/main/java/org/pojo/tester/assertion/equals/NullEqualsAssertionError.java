package org.pojo.tester.assertion.equals;


public class NullEqualsAssertionError extends EqualsAssertionError {

    private static final String CONSTRAINT_NULL = "The equals method should return false if object is comparing to null.\n"
                                                  + "Current implementation returns true.\n";

    NullEqualsAssertionError(final Class<?> testedCass) {
        super(testedCass);
    }

    @Override
    protected String getDetailedMessage() {
        return CONSTRAINT_NULL;
    }
}
