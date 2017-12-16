package pl.pojo.tester.internal.instantiator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pojo.tester.api.AbstractObjectInstantiator;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


abstract class AbstractMultiConstructorInstantiator extends AbstractInternalInstantiator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMultiConstructorInstantiator.class);
    private final List<AbstractObjectInstantiator> instantiators;

    AbstractMultiConstructorInstantiator(final Class<?> clazz, final List<AbstractObjectInstantiator> instantiators) {
        super(clazz);
        this.instantiators = instantiators;
    }

    protected Object createFindingBestConstructor() {
        final Optional<AbstractObjectInstantiator> instantiator = findFirstMatchingInPredefined();

        if (instantiator.isPresent()) {
            return instantiator.get()
                               .instantiate();
        }

        final Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        return Arrays.stream(constructors)
                     .map(this::createObjectFromConstructor)
                     .filter(Objects::nonNull)
                     .findAny()
                     .orElseThrow(this::createObjectInstantiationException);
    }

    private Optional<AbstractObjectInstantiator> findFirstMatchingInPredefined() {
        return instantiators.stream()
                            .filter(eachInstantiator -> clazz.equals(eachInstantiator.getClazz()))
                            .findFirst();
    }

    protected abstract Object createObjectFromArgsConstructor(final Class<?>[] parameterTypes, Object[] parameters);

    protected abstract Object createObjectFromNoArgsConstructor(final Constructor<?> constructor);

    protected abstract ObjectInstantiationException createObjectInstantiationException();

    private Object createObjectFromConstructor(final Constructor<?> constructor) {
        makeAccessible(constructor);
        if (constructor.getParameterCount() == 0) {
            return createObjectFromNoArgsConstructor(constructor);
        } else {
            try {
                final Object[] parameters = Instantiable.instantiateClasses(constructor.getParameterTypes(),
                                                                            instantiators);
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
