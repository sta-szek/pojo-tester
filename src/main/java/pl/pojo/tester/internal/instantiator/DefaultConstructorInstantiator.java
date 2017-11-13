package pl.pojo.tester.internal.instantiator;


import org.apache.commons.collections4.MultiValuedMap;
import pl.pojo.tester.api.ConstructorParameters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

class DefaultConstructorInstantiator extends AbstractObjectInstantiator {

    DefaultConstructorInstantiator(final Class<?> clazz,
                                   final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        super(clazz, constructorParameters);
    }

    @Override
    public Object instantiate() {
        try {
            final Constructor<?> defaultConstructor = clazz.getDeclaredConstructor();
            defaultConstructor.setAccessible(true);
            return defaultConstructor.newInstance();
        } catch (final NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new ObjectInstantiationException(clazz, e.getMessage(), e);
        }
    }

    @Override
    public boolean canInstantiate() {
        final Constructor<?>[] constructors = clazz.getConstructors();
        return !qualifiesForProxy(clazz) && Arrays.stream(constructors)
                                                  .filter(this::isNoArgs)
                                                  .anyMatch(this::isPublic);
    }

    private boolean qualifiesForProxy(final Class<?> clazz) {
        return clazz.isInterface() || clazz.isAnnotation() || Modifier.isAbstract(clazz.getModifiers());
    }

    private boolean isPublic(final Constructor<?> constructor) {
        return (constructor.getModifiers() & Modifier.PUBLIC) != 0;
    }

    private boolean isNoArgs(final Constructor<?> constructor) {
        return constructor.getParameterCount() == 0;
    }
}
