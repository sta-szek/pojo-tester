package org.pojo.tester.assertion;


import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    private final ResultBuilder resultBuilder;
    private final Object objectUnderAssert;
    private final Class<?> classUnderTest;

    ToStringAssertions(final ResultBuilder resultBuilder, final Object objectUnderAssert) {
        this.resultBuilder = resultBuilder;
        this.objectUnderAssert = objectUnderAssert;
        this.classUnderTest = objectUnderAssert.getClass();
    }


    public void contains(final String fieldName, final Object value) {
        final String stringValue = fieldName + "=" + getStringOf(value);
        String toString = "";
        try {
            toString = String.valueOf(getToStringMethod()
                                              .invoke(objectUnderAssert));
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
        }
        final boolean result = toString.contains(stringValue);

        final String message = formatMessage(CONSTRAINT_CONTAINS,
                                             classUnderTest.getCanonicalName(),
                                             stringValue,
                                             toString);
        appendResult(result, "contains", message);

    }

    public void doestNotContain(final String fieldName, final Object value) {
        final String stringValue = fieldName + "=" + getStringOf(value);
        final String toString = objectUnderAssert.toString();
        final boolean result = toString.contains(stringValue);

        final String message = formatMessage(CONSTRAINT_NOT_CONTAIN,
                                             classUnderTest.getCanonicalName(),
                                             stringValue,
                                             toString);
        appendResult(!result, "doesNotContain", message);
    }

    private Method getToStringMethod() {
        try {
            return objectUnderAssert.getClass()
                                    .getMethod("toString");
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException();
        }
    }

    private String getStringOf(final Object value) {
        final Object notNullObject = ObjectUtils.defaultIfNull(value, "");
        return notNullObject.toString();
    }

    private String formatMessage(final String message, final Object... objects) {
        return String.format(message, objects);
    }

    private void appendResult(final boolean pass, final String testName, final String errorMessage) {
        if (pass) {
            appendPass(classUnderTest, testName);
        } else {
            appendFail(classUnderTest, testName, errorMessage);
        }
    }

    private void appendFail(final Class<?> testedClass, final String testName, final String errorMessage) {
        resultBuilder.fail(testedClass, testName, errorMessage);
    }

    private void appendPass(final Class<?> testedClass, final String testName) {
        resultBuilder.pass(testedClass, testName);
    }
}
