package pl.pojo.tester.internal.instantiator;

import org.apache.commons.collections4.MultiValuedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pojo.tester.api.ConstructorParameters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

class UserDefinedConstructorInstantiator extends AbstractObjectInstantiator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BestConstructorInstantiator.class);

    UserDefinedConstructorInstantiator(final Class<?> clazz,
                                       final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        super(clazz, constructorParameters);
    }

    @Override
    public Object instantiate() {
        return constructorParameters.get(clazz)
                                    .stream()
                                    .map(this::createObjectUsingConstructorParameters)
                                    .filter(Objects::nonNull)
                                    .findAny()
                                    .orElseThrow(this::createObjectInstantiationException);
    }

    @Override
    public boolean canInstantiate() {
        return userDefinedConstructorParameters() && !qualifiesForProxy(clazz);
    }

    private boolean userDefinedConstructorParameters() {
        return constructorParameters.containsKey(clazz);
    }

    private boolean qualifiesForProxy(final Class<?> clazz) {
        return clazz.isInterface() || clazz.isAnnotation() || Modifier.isAbstract(clazz.getModifiers());
    }

    private ObjectInstantiationException createObjectInstantiationException() {
        return new ObjectInstantiationException(clazz,
                                                "Could not instantiate object by any user defined constructor types and parameters.");
    }

    private Object createObjectUsingConstructorParameters(final ConstructorParameters constructorParameters) {
        try {
            Class<?>[] constructorParametersTypes = constructorParameters.getParametersTypes();
            Object[] arguments = constructorParameters.getParameters();

            if (isInnerClass()) {
                constructorParametersTypes = putEnclosingClassAsFirstParameterType(clazz.getEnclosingClass(),
                                                                                   constructorParametersTypes);
                final Object enclosingClassInstance = instantiateEnclosingClass();
                arguments = putEnclosingClassInstanceAsFirstParameter(enclosingClassInstance, arguments);
            }

            final Constructor<?> constructor = clazz.getDeclaredConstructor(constructorParametersTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(arguments);
        } catch (final NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
            LOGGER.debug("Exception:", e);
            return null;
        }
    }


    private Object instantiateEnclosingClass() {
        final Class<?> enclosingClass = clazz.getEnclosingClass();
        return Instantiable.forClass(enclosingClass, constructorParameters)
                           .instantiate();
    }

    private Object[] putEnclosingClassInstanceAsFirstParameter(final Object enclosingClassInstance,
                                                               final Object[] arguments) {
        return Stream.concat(Stream.of(enclosingClassInstance), Arrays.stream(arguments))
                     .toArray(Object[]::new);
    }

    private Class[] putEnclosingClassAsFirstParameterType(final Class<?> enclosingClass,
                                                          final Class<?>[] constructorParametersTypes) {
        return Stream.concat(Stream.of(enclosingClass), Arrays.stream(constructorParametersTypes))
                     .toArray(Class[]::new);
    }

    private boolean isInnerClass() {
        return clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers());
    }

}
