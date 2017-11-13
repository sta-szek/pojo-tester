package pl.pojo.tester.internal.utils;


import pl.pojo.tester.api.PackageFilterException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.stream.Stream;

public final class ReflectionUtils {

    private static final String PACKAGE_SEPARATOR = ".";
    private static final String FILE_SEPARATOR = "/";
    private static final String CLASS_FILE_SUFFIX = ".class";

    private ReflectionUtils() {
    }

    public static Class<?>[] getClassesFromPackage(final String packageName) throws IOException {
        return getFilesFromPackage(packageName).toArray(Class[]::new);
    }

    private static Stream<? extends Class<?>> getFilesFromPackage(final String packageName) throws IOException {
        final Predicate<File> onlyFiles = file -> !file.isDirectory();
        final Path packagePath = ReflectionUtils.getFile(packageName)
                                                .toPath();
        return Files.walk(packagePath)
                    .map(Path::toFile)
                    .filter(onlyFiles)
                    .map(ReflectionUtils::replaceSlashesWithDots)
                    .map(fullPath -> extractFullyQualifiedClassName(fullPath, packageName))
                    .map(ReflectionUtils::removeClassSuffix)
                    .map(ClassLoader::loadClass);
    }

    private static String replaceSlashesWithDots(final File file) {
        return file.toString()
                   .replaceAll(Matcher.quoteReplacement(File.separator), PACKAGE_SEPARATOR);
    }

    private static String extractFullyQualifiedClassName(final String fullPath, final String packageName) {
        return fullPath.substring(fullPath.lastIndexOf(packageName));
    }


    private static String removeClassSuffix(final String classFile) {
        final int endIndex = classFile.length() - CLASS_FILE_SUFFIX.length();
        return classFile.substring(0, endIndex);
    }

    private static File getFile(final String packageName) {
        final String packagePath = packageName.replaceAll("\\" + PACKAGE_SEPARATOR, FILE_SEPARATOR);

        final URL fileUrl = Thread.currentThread()
                                  .getContextClassLoader()
                                  .getResource(packagePath);
        if (fileUrl == null) {
            throw new PackageFilterException(packagePath, null);
        }
        return new File(fileUrl.getFile());
    }
}