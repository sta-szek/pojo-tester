package pl.pojo.tester.internal.instantiator;


import org.apache.commons.collections4.MultiValuedMap;
import pl.pojo.tester.api.ConstructorParameters;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public final class Instantiable {

    private Instantiable() {
    }

    static Object[] instantiateClasses(final Class<?>[] classes,
                                       final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        return Arrays.stream(classes)
                     .map(clazz -> Instantiable.forClass(clazz, constructorParameters))
                     .map(AbstractObjectInstantiator::instantiate)
                     .toArray();
    }

    static AbstractObjectInstantiator forClass(final Class<?> clazz,
                                               final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        if (userDefinedConstructorParametersFor(clazz, constructorParameters) && !qualifiesForProxy(clazz)) {
            return new UserDefinedConstructorInstantiator(clazz, constructorParameters);
        }

        if (isStringClass(clazz)) {
            return new StringClassInstantiator();
        }

        if (isKindOfCollectionClass(clazz)) {
            return new CollectionInstantiator(clazz);
        }

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
            return new ProxyInstantiator(clazz, constructorParameters);
        }

        return new BestConstructorInstantiator(clazz, constructorParameters);
    }

    private static boolean isKindOfCollectionClass(final Class<?> clazz) {
        return Iterator.class.isAssignableFrom(clazz)
                || Iterable.class.isAssignableFrom(clazz)
                || Map.class.isAssignableFrom(clazz)
                || Stream.class.isAssignableFrom(clazz);
    }

    private static boolean userDefinedConstructorParametersFor(final Class<?> clazz,
                                                               final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        return constructorParameters.containsKey(clazz);
    }

    private static boolean isStringClass(final Class<?> clazz) {
        return clazz.equals(String.class);
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
                                                  .anyMatch(Instantiable::isPublic);
    }

    private static boolean isPublic(final Constructor<?> constructor) {
        return (constructor.getModifiers() & Modifier.PUBLIC) != 0;
    }

    private static boolean isNoArgs(final Constructor<?> constructor) {
        return constructor.getParameterCount() == 0;
    }
}
