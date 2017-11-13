package pl.pojo.tester.internal.utils;

public final class ClassLoader {

    private ClassLoader() {
    }

    public static Class<?> loadClass(final String qualifiedClassName) {
        try {
            return Class.forName(qualifiedClassName);
        } catch (final ClassNotFoundException e) {
            throw new ClassLoadingException(qualifiedClassName, e);
        }
    }
}
