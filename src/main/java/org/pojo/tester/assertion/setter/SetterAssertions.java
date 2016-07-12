package org.pojo.tester.assertion.setter;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import org.pojo.tester.utils.FieldUtils;

public class SetterAssertions {

    private final Object objectUnderAssert;
    private final Class<?> classUnderTest;

    public SetterAssertions(final Object objectUnderAssert) {
        this.objectUnderAssert = objectUnderAssert;
        this.classUnderTest = objectUnderAssert.getClass();
    }

    public void willSetValueOnField(final Method setter, final Field field, final Object expectedValue)
            throws IllegalAccessException, InvocationTargetException {
        setter.invoke(objectUnderAssert, expectedValue);
        final Object value = FieldUtils.getValue(objectUnderAssert, field);
        final boolean result = Objects.equals(value, expectedValue);

        checkResult(result, new SetterAssertionError(classUnderTest, field, expectedValue, value));
    }


    private void checkResult(final boolean pass, final SetterAssertionError errorToThrow) {
        if (!pass) {
            throw errorToThrow;
        }
    }
}
