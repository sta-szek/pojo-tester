package org.pojo.tester.assertion.getter;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import org.pojo.tester.utils.FieldUtils;

public class GetterAssertions {

    private final Object objectUnderAssert;
    private final Class<?> classUnderTest;

    public GetterAssertions(final Object objectUnderAssert) {
        this.objectUnderAssert = objectUnderAssert;
        this.classUnderTest = objectUnderAssert.getClass();
    }

    public void willGetValueFromField(final Method getter, final Field field)
            throws IllegalAccessException, InvocationTargetException {
        final Object valueFromGetter = getter.invoke(objectUnderAssert);
        final Object value = FieldUtils.getValue(objectUnderAssert, field);
        final boolean result = Objects.equals(value, valueFromGetter);

        checkResult(result, new GetterAssertionError(classUnderTest, field, valueFromGetter, value));
    }


    private void checkResult(final boolean pass, final GetterAssertionError errorToThrow) {
        if (!pass) {
            throw errorToThrow;
        }
    }
}
