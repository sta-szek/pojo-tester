package pl.pojo.tester.internal.instantiator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;
import pl.pojo.tester.api.ConstructorParameters;

class UserDefinedConstructorInstantiator extends ObjectInstantiator {

    private final Map<Class<?>, ConstructorParameters> constructorInfo;

    UserDefinedConstructorInstantiator(final Class<?> clazz, final Map<Class<?>, ConstructorParameters> constructorInfo) {
        super(clazz);
        this.constructorInfo = constructorInfo;
    }

    @Override
    public Object instantiate() {
        try {
            final ConstructorParameters constructorParameters = this.constructorInfo.get(clazz);
            Class<?>[] constructorParametersTypes = constructorParameters.getConstructorParametersTypes();
            Object[] arguments = constructorParameters.getConstructorParameters();

            if (isInnerClass()) {
                constructorParametersTypes = putEnclosingClassAsFirstParameterType(clazz.getEnclosingClass(), constructorParametersTypes);
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
        return Instantiable.forClass(enclosingClass, constructorInfo)
                           .instantiate();
    }

    private Object[] putEnclosingClassInstanceAsFirstParameter(final Object enclosingClassInstance, final Object[] arguments) {
        return Stream.concat(Stream.of(enclosingClassInstance), Arrays.stream(arguments))
                     .toArray(Object[]::new);
    }


    private Class[] putEnclosingClassAsFirstParameterType(final Class<?> enclosingClass, final Class<?>[] constructorParametersTypes) {
        return Stream.concat(Stream.of(enclosingClass), Arrays.stream(constructorParametersTypes))
                     .toArray(Class[]::new);
    }

    private boolean isInnerClass() {
        return clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers());
    }

}
