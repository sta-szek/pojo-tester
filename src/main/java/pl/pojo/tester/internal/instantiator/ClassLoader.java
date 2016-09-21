package pl.pojo.tester.internal.instantiator;

public class ClassLoader {

    public static Class<?> loadClass(final String qualifiedClassName) {
        try {
            return Class.forName(qualifiedClassName);
        } catch (final ClassNotFoundException e) {
            throw new ClassLoadingException(qualifiedClassName, e);
        }
    }
}
