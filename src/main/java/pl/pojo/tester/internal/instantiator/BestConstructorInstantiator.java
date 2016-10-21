package pl.pojo.tester.internal.instantiator;


import org.apache.commons.collections4.MultiValuedMap;
import pl.pojo.tester.api.ConstructorParameters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;

class BestConstructorInstantiator extends ObjectInstantiator {

    private final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters;

    BestConstructorInstantiator(final Class<?> clazz, final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        super(clazz);
        this.constructorParameters = constructorParameters;
    }

    @Override
    public Object instantiate() {
        return createFindingBestConstructor(clazz);
    }

    private Object createFindingBestConstructor(final Class<?> clazz) {
        final Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        return Arrays.stream(constructors)
                     .map(this::createObjectFromConstructor)
                     .filter(Objects::nonNull)
                     .findAny()
                     .orElseThrow(this::createObjectInstantiationException);
    }

    private ObjectInstantiationException createObjectInstantiationException() {
        return new ObjectInstantiationException(clazz, "Class could not be created by any constructor.");
    }

    private Object createObjectFromConstructor(final Constructor<?> constructor) {
        makeAccessible(constructor);
        if (constructor.getParameterCount() == 0) {
            return createObjectFromNoArgsConstructor(constructor);
        } else {
            final Class<?>[] parameterTypes = constructor.getParameterTypes();
            try {
                final Object[] parameters = Arrays.stream(parameterTypes)
                                                  .map(this::instantiate)
                                                  .toArray();
                return constructor.newInstance(parameters);
            } catch (final InstantiationException
                    | IllegalAccessException
                    | InvocationTargetException
                    | SecurityException
                    | IllegalArgumentException
                    | ObjectInstantiationException e) {
                // ignore, we want to try all constructors
                // if all constructors fail, it will be handled by caller
                return null;
            }
        }
    }

    private Object instantiate(final Class<?> clazz) {
        return Instantiable.forClass(clazz, constructorParameters)
                           .instantiate();
    }

    private Object createObjectFromNoArgsConstructor(final Constructor<?> constructor) {
        try {
            return constructor.newInstance();
        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // ignore, we want to try all constructors
            // if all constructors fail, it will be handled by caller
            return null;
        }
    }

    private void makeAccessible(final Constructor<?> constructor) {
        constructor.setAccessible(true);
    }
}
