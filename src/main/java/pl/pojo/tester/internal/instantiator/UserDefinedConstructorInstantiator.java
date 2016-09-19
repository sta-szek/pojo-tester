package pl.pojo.tester.internal.instantiator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

class UserDefinedConstructorInstantiator extends ObjectInstantiator {

    private final Map<Class<?>, Object[]> classAndConstructorParameters;

    UserDefinedConstructorInstantiator(final Class<?> clazz, final Map<Class<?>, Object[]> classAndConstructorParameters) {
        super(clazz);
        this.classAndConstructorParameters = classAndConstructorParameters;
    }

    @Override
    public Object instantiate() {
        try {
            final Object[] constructorParameters = classAndConstructorParameters.get(clazz);
            Class[] constructorParametersTypes = convertToParameterTypes(constructorParameters);
            Object[] arguments = constructorParameters;

            if (isInnerClass()) {
                constructorParametersTypes = putEnclosingClassAsFirstParameterType(clazz.getEnclosingClass(), constructorParameters);
                final Object enclosingClassInstance = instantiateEnclosingClass();
                arguments = putEnclosingClassInstanceAsFirstParameter(enclosingClassInstance, arguments);
            }

            final Constructor<?> constructor = clazz.getDeclaredConstructor(constructorParametersTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(arguments);
        } catch (final NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
            throw new ObjectInstantiationException(clazz, e);
        }
    }

    private Object instantiateEnclosingClass() {
        final Class<?> enclosingClass = clazz.getEnclosingClass();
        return Instantiable.forClass(enclosingClass, classAndConstructorParameters)
                           .instantiate();
    }

    private Object[] putEnclosingClassInstanceAsFirstParameter(final Object enclosingClassInstance, final Object[] arguments) {
        return Stream.concat(Stream.of(enclosingClassInstance), Arrays.stream(arguments))
                     .toArray(Object[]::new);
    }


    private Class[] putEnclosingClassAsFirstParameterType(final Class<?> enclosingClass, final Object[] constructorParameters) {
        final Class[] parameterTypes = convertToParameterTypes(constructorParameters);
        return Stream.concat(Stream.of(enclosingClass), Arrays.stream(parameterTypes))
                     .toArray(Class[]::new);
    }

    private boolean isInnerClass() {
        return clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers());
    }

    private Class[] convertToParameterTypes(final Object[] constructorParameters) {
        return Arrays.stream(constructorParameters)
                     .map(Object::getClass)
                     .toArray(Class[]::new);
    }
}
