package org.pojo.tester.instantiator;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

class BestConstructorInstantiator extends ObjectInstantiator {

    BestConstructorInstantiator(final Class<?> clazz) {
        super(clazz);
    }

    @Override
    public Object instantiate() {
        return createFindingBestConstructor(clazz);
    }

    private Object createFindingBestConstructor(final Class<?> clazz) {
        final Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        return Arrays.stream(constructors)
                     .map(this::createObjectFromConstructor)
                     .filter(object -> object != null)
                     .findAny()
                     .orElseThrow(() -> new ObjectInstantiationException(clazz, "Class could not be created by any constructor."));
    }

    private Object createObjectFromConstructor(final Constructor<?> constructor) {
        makeAccessible(constructor);
        if (constructor.getParameterCount() == 0) {
            return createObjectFromNoArgsConstructor(constructor);
        } else {
            final Class<?>[] parameterTypes = constructor.getParameterTypes();
            final Object[] parameters = Arrays.stream(parameterTypes)
                                              .map(this::instantiate)
                                              .toArray();
            try {
                return constructor.newInstance(parameters);
            } catch (final InstantiationException
                    | IllegalAccessException
                    | InvocationTargetException
                    | SecurityException
                    | IllegalArgumentException e) {
                // ignore, we want to try all constructors
                // if all constructors fail, it will be handled by caller
                return null;
            }
        }
    }

    private Object instantiate(final Class<?> clazz) {
        return Instantiable.forClass(clazz)
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
