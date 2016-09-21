package pl.pojo.tester.internal.instantiator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ClassLoader {

    public static Class<?> loadClass(final String qualifiedClassName) {
        try {
            return Class.forName(qualifiedClassName);
        } catch (final ClassNotFoundException e) {
            throw new ClassLoadingException(qualifiedClassName, e);
        }
    }
}
