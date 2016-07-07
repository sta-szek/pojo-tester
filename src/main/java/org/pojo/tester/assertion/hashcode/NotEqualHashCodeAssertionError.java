package org.pojo.tester.assertion.hashcode;


public class NotEqualHashCodeAssertionError extends HashCodeAssertionError {

    private static final String CONSTRAINT_NOT_EQUAL = "The hashCode method should return different hash codes for non equal objects.\n"
                                                       + "Current implementation returns same hash codes.\n"
                                                       + "Object:\n"
                                                       + "%s\n"
                                                       + "and\n"
                                                       + "%s\n"
                                                       + "have should have different hash codes:\n"
                                                       + "%s\n"
                                                       + "and\n"
                                                       + "%s";
    private final Object testedObject;
    private final Object secondObject;
    private final int firstHashCode;
    private final int secondHashCode;

    NotEqualHashCodeAssertionError(final Class<?> testedCass,
                                   final Object testedObject,
                                   final Object secondObject,
                                   final int firstHashCode,
                                   final int secondHashCode) {
        super(testedCass);
        this.testedObject = testedObject;
        this.secondObject = secondObject;
        this.firstHashCode = firstHashCode;
        this.secondHashCode = secondHashCode;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_NOT_EQUAL, testedObject, secondObject, firstHashCode, secondHashCode);
    }
}
