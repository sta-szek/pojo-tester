package pl.pojo.tester.internal.assertion.getter;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import pl.pojo.tester.internal.utils.FieldUtils;

public class GetterAssertions {

    private final Object objectUnderAssert;
    private final Class<?> classUnderTest;

    public GetterAssertions(final Object objectUnderAssert) {
        this.objectUnderAssert = objectUnderAssert;
        this.classUnderTest = objectUnderAssert.getClass();
    }

    public void willGetValueFromField(final Method getter, final Field field)
            throws IllegalAccessException, InvocationTargetException {
        getter.setAccessible(true);
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
