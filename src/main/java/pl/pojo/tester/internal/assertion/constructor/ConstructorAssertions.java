package pl.pojo.tester.internal.assertion.constructor;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorAssertions {

    private final Constructor<?> constructorUnderAssert;
    private final Class<?> classUnderTest;

    public ConstructorAssertions(final Constructor<?> constructorUnderAssert) {
        this.constructorUnderAssert = constructorUnderAssert;
        this.classUnderTest = constructorUnderAssert.getDeclaringClass();
    }

    public void willInstantiateClassUsing(final Object... constructorParameters) {
        constructorUnderAssert.setAccessible(true);
        try {
            constructorUnderAssert.newInstance(constructorParameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ConstructorAssertionError(classUnderTest, constructorUnderAssert, constructorParameters, e);
        }

    }

}
