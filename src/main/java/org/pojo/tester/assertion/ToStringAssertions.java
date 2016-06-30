package org.pojo.tester.assertion;


import org.apache.commons.lang3.ObjectUtils;

public class ToStringAssertions {
    private static final String BAD_TO_STRING = "Class %s has bad 'toString' method implementation.";
    private static final String OBJECT_AND_NEW_LINE = "%s\n";

    private static final String CONSTRAINT_CONTAINS = BAD_TO_STRING
                                                      + "The toString method should contain:\n"
                                                      + OBJECT_AND_NEW_LINE
                                                      + "But does not.\n"
                                                      + "Result of toString:"
                                                      + OBJECT_AND_NEW_LINE;

    private static final String CONSTRAINT_NOT_CONTAIN = BAD_TO_STRING
                                                         + "The toString method should not contain:\n"
                                                         + OBJECT_AND_NEW_LINE
                                                         + "But does.\n"
                                                         + "Result of toString:"
                                                         + OBJECT_AND_NEW_LINE;

    private final Object objectUnderAssert;
    private final Class<?> classUnderTest;

    ToStringAssertions(final Object objectUnderAssert) {
        this.objectUnderAssert = objectUnderAssert;
        this.classUnderTest = objectUnderAssert.getClass();
    }


    public void contains(final String fieldName, final Object value) {
        final String stringValue = fieldName + "=" + getStringOf(value);
        final String toString = objectUnderAssert.toString();
        final boolean result = toString.contains(stringValue);

        final String message = formatMessage(CONSTRAINT_CONTAINS,
                                             classUnderTest.getCanonicalName(),
                                             stringValue,
                                             toString);
        checkResult(result, message);

    }

    public void doestNotContain(final String fieldName, final Object value) {
        final String stringValue = fieldName + "=" + getStringOf(value);
        final String toString = objectUnderAssert.toString();
        final boolean result = toString.contains(stringValue);

        final String message = formatMessage(CONSTRAINT_NOT_CONTAIN,
                                             classUnderTest.getCanonicalName(),
                                             stringValue,
                                             toString);
        checkResult(!result, message);
    }

    private String getStringOf(final Object value) {
        final Object notNullObject = ObjectUtils.defaultIfNull(value, "");
        return notNullObject.toString();
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
