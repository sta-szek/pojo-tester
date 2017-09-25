package pl.pojo.tester.internal.assertion.getter;


import pl.pojo.tester.internal.GetOrSetValueException;
import pl.pojo.tester.internal.utils.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class GetterAssertions {

    private final Object objectUnderAssert;
    private final Class<?> classUnderTest;

    public GetterAssertions(final Object objectUnderAssert) {
        this.objectUnderAssert = objectUnderAssert;
        this.classUnderTest = objectUnderAssert.getClass();
    }

    public void willGetValueFromField(final Method getter, final Field field) {
        try {
            getter.setAccessible(true);
            final Object valueFromGetter;
            valueFromGetter = getter.invoke(objectUnderAssert);
            final Object value = FieldUtils.getValue(objectUnderAssert, field);
            final boolean result = Objects.deepEquals(value, valueFromGetter);

            checkResult(result, new GetterAssertionError(classUnderTest, field, valueFromGetter, value));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new GetOrSetValueException(field.getName(), classUnderTest, e);
        }
    }


    private void checkResult(final boolean pass, final GetterAssertionError errorToThrow) {
        if (!pass) {
            throw errorToThrow;
        }
    }
}
