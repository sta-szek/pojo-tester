package pl.pojo.tester.internal.instantiator;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class DefaultConstructorInstantiator extends ObjectInstantiator {

    DefaultConstructorInstantiator(final Class<?> clazz) {
        super(clazz);
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
}
