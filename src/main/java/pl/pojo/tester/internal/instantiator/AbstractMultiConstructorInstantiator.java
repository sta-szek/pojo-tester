package pl.pojo.tester.internal.instantiator;


import org.apache.commons.collections4.MultiValuedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pojo.tester.api.ConstructorParameters;
import pl.pojo.tester.internal.utils.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;


abstract class AbstractMultiConstructorInstantiator extends AbstractObjectInstantiator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMultiConstructorInstantiator.class);

    AbstractMultiConstructorInstantiator(final Class<?> clazz,
                                         final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        super(clazz, constructorParameters);
    }

    protected Object instantiateUsingUserParameters() {
        final Collection<ConstructorParameters> userConstructorParameters = constructorParameters.get(clazz);
        if (userDefinedOwnParametersForThisClass(userConstructorParameters)) {
            final Object result = tryToInstantiateUsing(userConstructorParameters);
            if (result != null) {
                return result;
            }
            LOGGER.warn("Could not instantiate class {} with user defined parameters. "
                                + "Trying create instance finding best constructor", clazz);
        }
        return null;
    }

    private boolean userDefinedOwnParametersForThisClass(final Collection<ConstructorParameters> userConstructorParameters) {
        return CollectionUtils.isNotEmpty(userConstructorParameters);
    }

    private Object tryToInstantiateUsing(final Collection<ConstructorParameters> userConstructorParameters) {
        for (final ConstructorParameters param : userConstructorParameters) {
            Class<?>[] parameterTypes = param.getParametersTypes();
            try {
                Object[] parameters = param.getParameters();
                if (isInnerClass()) {
                    parameterTypes = putEnclosingClassAsFirstParameterType(clazz.getEnclosingClass(), parameterTypes);
                    final Object enclosingClassInstance = instantiateEnclosingClass();
                    parameters = putEnclosingClassInstanceAsFirstParameter(enclosingClassInstance, parameters);
                }
                return createObjectFromArgsConstructor(parameterTypes, parameters);
            } catch (final ObjectInstantiationException e) {
                LOGGER.debug("ObjectInstantiationException:", e);
                // ignore, try all user defined constructor parameters and types
            }
        }
        return null;
    }

    protected Object createFindingBestConstructor() {
        final Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        return Arrays.stream(constructors)
                     .map(this::createObjectFromConstructor)
                     .filter(Objects::nonNull)
                     .findAny()
                     .orElseThrow(this::createObjectInstantiationException);
    }

    protected abstract Object createObjectFromArgsConstructor(final Class<?>[] parameterTypes, Object[] parameters);

    protected abstract Object createObjectFromNoArgsConstructor(final Constructor<?> constructor);

    protected abstract ObjectInstantiationException createObjectInstantiationException();

    private Object instantiateEnclosingClass() {
        final Class<?> enclosingClass = clazz.getEnclosingClass();
        return Instantiable.forClass(enclosingClass, constructorParameters)
                           .instantiate();
    }

    private Class[] putEnclosingClassAsFirstParameterType(final Class<?> enclosingClass,
                                                          final Class<?>[] constructorParametersTypes) {
        return Stream.concat(Stream.of(enclosingClass), Arrays.stream(constructorParametersTypes))
                     .toArray(Class[]::new);
    }

    private boolean isInnerClass() {
        return clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers());
    }

    private Object[] putEnclosingClassInstanceAsFirstParameter(final Object enclosingClassInstance,
                                                               final Object[] arguments) {
        return Stream.concat(Stream.of(enclosingClassInstance), Arrays.stream(arguments))
                     .toArray(Object[]::new);
    }

    protected Object createObjectFromConstructor(final Constructor<?> constructor) {
        makeAccessible(constructor);
        if (constructor.getParameterCount() == 0) {
            return createObjectFromNoArgsConstructor(constructor);
        } else {
            try {
                final Object[] parameters = Instantiable.instantiateClasses(constructor.getParameterTypes(),
                                                                            constructorParameters);
                return createObjectFromArgsConstructor(constructor.getParameterTypes(), parameters);
            } catch (final Exception e) {
                LOGGER.debug("Exception:", e);
                // ignore, we want to try all constructors
                // if all constructors fail, it will be handled by caller
                return null;
            }
        }
    }

    private void makeAccessible(final Constructor<?> constructor) {
        constructor.setAccessible(true);
    }
}
