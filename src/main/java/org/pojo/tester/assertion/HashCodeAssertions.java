package org.pojo.tester.assertion;


public class HashCodeAssertions {
    private static final String BAD_HASH_CODE = "Class %s has bad 'hashCode' method implementation.";
    private static final String OBJECT_AND_NEW_LINE = "%s\n";
    private static final String TWO_HASH_CODES_AND_NEW_LINE = "%s\n and\n %s\n";
    private static final String CONSTRAINT_CONSISTENT = BAD_HASH_CODE
                                                        + "The hashCode method should return same hash code for same object."
                                                        + "Current implementation returns different values.\n"
                                                        + "Object:\n"
                                                        + OBJECT_AND_NEW_LINE
                                                        + "has two different hash codes:"
                                                        + TWO_HASH_CODES_AND_NEW_LINE;

    private static final String CONSTRAINT_EQUAL = BAD_HASH_CODE
                                                   + "The hashCode method should return same hash code for equal objects."
                                                   + "Current implementation returns different values.\n"
                                                   + "Object:\n"
                                                   + OBJECT_AND_NEW_LINE
                                                   + "and\n"
                                                   + OBJECT_AND_NEW_LINE
                                                   + "have two different hash codes:"
                                                   + TWO_HASH_CODES_AND_NEW_LINE;

    private static final String CONSTRAINT_NOT_EQUAL = BAD_HASH_CODE
                                                       + "The hashCode method should return different hash codes for non equal objects.\n"
                                                       + "Current implementation returns same hash codes.\n"
                                                       + "Object:\n"
                                                       + OBJECT_AND_NEW_LINE
                                                       + "and\n"
                                                       + OBJECT_AND_NEW_LINE
                                                       + "have same hash codes:"
                                                       + TWO_HASH_CODES_AND_NEW_LINE;

    private final Object objectUnderAssert;
    private final Class<?> classUnderTest;

    HashCodeAssertions(final Object objectUnderAssert) {
        this.objectUnderAssert = objectUnderAssert;
        this.classUnderTest = objectUnderAssert.getClass();
    }

    public void isConsistent() {
        final int result1 = objectUnderAssert.hashCode();
        final int result2 = objectUnderAssert.hashCode();
        final boolean result = result1 == result2;
        final String message = formatMessage(CONSTRAINT_CONSISTENT,
                                             classUnderTest.getCanonicalName(),
                                             objectUnderAssert,
                                             result1,
                                             result2);
        checkResult(result, message);
    }

    public void returnsSameValueFor(final Object otherObject) {
        final int result1 = objectUnderAssert.hashCode();
        final int result2 = otherObject.hashCode();
        final boolean result = result1 == result2;
        final String message = formatMessage(CONSTRAINT_EQUAL,
                                             classUnderTest.getCanonicalName(),
                                             objectUnderAssert,
                                             otherObject,
                                             result1,
                                             result2);
        checkResult(result, message);
    }

    public void returnsDifferentValueFor(final Object otherObject) {
        final int result1 = objectUnderAssert.hashCode();
        final int result2 = otherObject.hashCode();
        final boolean result = result1 == result2;
        final String message = formatMessage(CONSTRAINT_NOT_EQUAL,
                                             classUnderTest.getCanonicalName(),
                                             objectUnderAssert,
                                             otherObject,
                                             result1,
                                             result2);
        checkResult(!result, message);
    }

    private String formatMessage(final String message, final Object... objects) {
        return String.format(message, objects);
    }

    private void checkResult(final boolean pass, final String errorMessage) {
        if (!pass) {
            throw new AssertionException(errorMessage);
        }
    }

}
