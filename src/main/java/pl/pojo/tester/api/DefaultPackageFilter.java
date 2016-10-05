package pl.pojo.tester.api;


import pl.pojo.tester.internal.instantiator.ClassLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.regex.Matcher;

import static pl.pojo.tester.internal.preconditions.ParameterPreconditions.checkNotBlank;

/**
 * Default package filter filters classes from package name recursively.
 *
 * @author Piotr Joński
 * @since 0.5.0
 */
public class DefaultPackageFilter implements PackageFilter {

    private static final String PACKAGE_SEPARATOR = ".";
    private static final String FILE_SEPARATOR = "/";
    private static final String CLASS_FILE_SUFFIX = ".class";


    private final File packageFile;
    private final String packageName;

    private DefaultPackageFilter(final String packageName) {
        checkNotBlank("packageName", packageName);
        this.packageName = packageName;
        this.packageFile = getFile(packageName);
    }

    /**
     * Creates filter for package name.
     *
     * @param packageName name of package
     *
     * @return filter for package name
     */
    public static DefaultPackageFilter forPackage(final String packageName) {
        return new DefaultPackageFilter(packageName);
    }

    /**
     * Creates filter for package of given class.
     *
     * @param clazz class
     *
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
            final Predicate<File> onlyFiles = file -> !file.isDirectory();

            return Files.walk(packageFile.toPath())
                        .map(Path::toFile)
                        .filter(onlyFiles)
                        .map(this::replaceSlashesWithDots)
                        .map(this::extractPackageName)
                        .map(this::removeClassSuffix)
                        .map(ClassLoader::loadClass)
                        .toArray(Class[]::new);
        } catch (final IOException e) {
            throw new PacakgeFilterException(packageFile.toString(), e);
        }
    }

    private String replaceSlashesWithDots(final File file) {
        return file.toString()
                   .replaceAll(Matcher.quoteReplacement(File.separator), PACKAGE_SEPARATOR);
    }

    private String extractPackageName(final String dottedString) {
        return dottedString.substring(dottedString.indexOf(packageName));
    }


    private String removeClassSuffix(final String classFile) {
        final int endIndex = classFile.length() - CLASS_FILE_SUFFIX.length();
        return classFile.substring(0, endIndex);
    }

    private File getFile(final String packageName) {
        final String packagePath = packageName.replaceAll("\\" + PACKAGE_SEPARATOR, FILE_SEPARATOR);

        final URL fileUrl = Thread.currentThread()
                                  .getContextClassLoader()
                                  .getResource(packagePath);
        if (fileUrl == null) {
            throw new PacakgeFilterException(packagePath, null);
        }
        return new File(fileUrl.getFile());
    }

}
