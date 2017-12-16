package pl.pojo.tester.internal.instantiator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pojo.tester.api.AbstractObjectInstantiator;
import pl.pojo.tester.api.ConstructorParameters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

public class UserDefinedConstructorInstantiator extends AbstractObjectInstantiator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BestConstructorInstantiator.class);
    private final ConstructorParameters constructorParameters;

    public UserDefinedConstructorInstantiator(final Class<?> clazz, final ConstructorParameters constructorParameters) {
        super(clazz);
        this.constructorParameters = constructorParameters;
    }

    @Override
    public Object instantiate() {
        return createObjectUsingConstructorParameters(constructorParameters)
                .orElseThrow(this::createObjectInstantiationException);
    }

    private Optional<Object> createObjectUsingConstructorParameters(final ConstructorParameters constructorParameters) {
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
            return Optional.of(constructor.newInstance(arguments));
        } catch (final NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
            LOGGER.debug("Exception:", e);
            return Optional.empty();
        }
    }

    private ObjectInstantiationException createObjectInstantiationException() {
        return new ObjectInstantiationException(clazz,
                                                "Could not instantiate object by any user defined constructor types and parameters.");
    }


    private Object instantiateEnclosingClass() {
        final Class<?> enclosingClass = clazz.getEnclosingClass();
        // TODO create GH issue and explain why creating enclosing class may fail:
        // it will fail if one of enclosing class constructor parameter requires user defined instantiator
        // currently there is no workaround for this issue
        return Instantiable.forClass(enclosingClass, new LinkedList<>())
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

    @Override
    public String toString() {
        return "UserDefinedConstructorInstantiator{constructorParameters=" + constructorParameters + ", clazz=" + clazz + '}';
    }
}
