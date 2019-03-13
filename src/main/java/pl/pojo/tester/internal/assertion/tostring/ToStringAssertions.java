package pl.pojo.tester.internal.assertion.tostring;


import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToStringAssertions {

    private final Object objectUnderAssert;
    private final Class<?> classUnderTest;

    public ToStringAssertions(final Object objectUnderAssert) {
        this.objectUnderAssert = objectUnderAssert;
        this.classUnderTest = objectUnderAssert.getClass();
    }


    public void contains(final String fieldName, final Object value) {
        final String stringValue = fieldName + "=" + getStringOf(value);
        final String toString = objectUnderAssert.toString();
        final boolean result = toString.contains(stringValue);
        checkResult(result, new ContainsToStringAssertionError(classUnderTest, stringValue, toString));

    }

    public void doestNotContain(final String fieldName, final Object value) {
        final String stringValue = fieldName + "=" + getStringOf(value);
        final String toString = objectUnderAssert.toString();
        final boolean result = toString.contains(stringValue);
        checkResult(!result, new NotContainToStringAssertionError(classUnderTest, stringValue, toString));

    }

    private String getStringOf(final Object value) {
        if (value == null) {
            return "";
        } else if (value.getClass().isArray()) {
            final int length = Array.getLength(value);
            final Object[] array = new Object[length];
            for (int i = 0; i < length; i++) {
                array[i] = Array.get(value, i);
            }
            return Arrays.toString(array);
        } else {
            return value.toString();
        }
    }

    private void checkResult(final boolean pass, final AbstractToStringAssertionError errorToThrow) {
        if (!pass) {
            throw errorToThrow;
        }
    }
}
