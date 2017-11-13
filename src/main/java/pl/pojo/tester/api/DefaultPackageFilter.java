package pl.pojo.tester.api;


import pl.pojo.tester.internal.utils.ReflectionUtils;

import java.io.IOException;

import static pl.pojo.tester.internal.preconditions.ParameterPreconditions.checkNotBlank;

/**
 * Default package filter filters classes from package name recursively.
 *
 * @author Piotr Joński
 * @since 0.5.0
 */
public final class DefaultPackageFilter implements PackageFilter {

    private final String packageName;

    private DefaultPackageFilter(final String packageName) {
        checkNotBlank("packageName", packageName);
        this.packageName = packageName;
    }

    /**
     * Creates filter for package name.
     *
     * @param packageName name of package
     * @return filter for package name
     */
    public static DefaultPackageFilter forPackage(final String packageName) {
        return new DefaultPackageFilter(packageName);
    }

    /**
     * Creates filter for package of given class.
     *
     * @param clazz class
     * @return filter for class package
     */
    public static DefaultPackageFilter forClass(final Class<?> clazz) {
        return new DefaultPackageFilter(clazz.getPackage()
                                             .getName());
    }

    /**
     * {@inheritDoc}
     *
     * @return filtered classes
     */
    @Override
    public Class<?>[] getClasses() {
        try {
            return ReflectionUtils.getClassesFromPackage(packageName);
        } catch (final IOException e) {
            throw new PackageFilterException(packageName, e);
        }
    }
}
