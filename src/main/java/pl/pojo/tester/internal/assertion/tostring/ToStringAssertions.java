package pl.pojo.tester.internal.assertion.tostring;


import java.util.Arrays;
import java.util.Objects;

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
        if(Objects.isNull(value)) {
            return ""; //empty string as default
        } else if(value.getClass().isArray()){
            return Arrays.toString((Object[])value);
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
