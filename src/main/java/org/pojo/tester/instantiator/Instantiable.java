package org.pojo.tester.instantiator;


import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public abstract class Instantiable {

    public static ObjectInstantiator forClass(final Class<?> clazz) {
        if (canBeCreatedByDefaultConstructor(clazz)) {
            return new DefaultConstructorInstantiator(clazz);
        }

        if (isPrimitiveOrWrapper(clazz)) {
            return new PrimitiveInstantiator(clazz);
        }

        if (clazz.isEnum()) {
            return new EnumInstantiator(clazz);
        }

        if (clazz.isArray()) {
            return new ArrayInstantiator(clazz);
        }

        if (qualifiesForProxy(clazz)) {
            return new ProxyInstantiator(clazz);
        }

        return new BestConstructorInstantiator(clazz);

    }

    private static boolean qualifiesForProxy(final Class<?> clazz) {
        return clazz.isInterface() || clazz.isAnnotation() || Modifier.isAbstract(clazz.getModifiers());
    }

    private static boolean isPrimitiveOrWrapper(final Class<?> clazz) {
        return clazz.isPrimitive() || isWrapper(clazz);
    }

    private static boolean isWrapper(final Class<?> clazz) {
        return clazz == Double.class
               || clazz == Float.class
               || clazz == Long.class
               || clazz == Integer.class
               || clazz == Short.class
               || clazz == Character.class
               || clazz == Byte.class
               || clazz == Boolean.class;
    }

    private static boolean canBeCreatedByDefaultConstructor(final Class<?> clazz) {
        final Constructor<?>[] constructors = clazz.getConstructors();
        return !qualifiesForProxy(clazz) && Arrays.stream(constructors)
                                                  .filter(Instantiable::isNoArgs)
                                                  .filter(Instantiable::isPublic)
                                                  .findAny()
                                                  .isPresent();
    }

    private static boolean isPublic(final Constructor<?> constructor) {
        return (constructor.getModifiers() & Modifier.PUBLIC) != 0;
    }

    private static boolean isNoArgs(final Constructor<?> constructor) {
        return constructor.getParameterCount() == 0;
    }
}
