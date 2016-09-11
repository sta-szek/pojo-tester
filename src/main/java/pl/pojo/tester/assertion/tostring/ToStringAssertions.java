package pl.pojo.tester.assertion.tostring;


import org.apache.commons.lang3.ObjectUtils;

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
        final Object notNullObject = ObjectUtils.defaultIfNull(value, "");
        return notNullObject.toString();
    }


    private void checkResult(final boolean pass, final ToStringAssertionError errorToThrow) {
        if (!pass) {
            throw errorToThrow;
        }
    }
}
