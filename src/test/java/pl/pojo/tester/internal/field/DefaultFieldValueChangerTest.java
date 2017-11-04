package pl.pojo.tester.internal.field;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.internal.field.date.DefaultDateAndTimeFieldValueChanger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultFieldValueChangerTest {

    @Test
    void Should_Contains_All_Field_Value_Changers_From_Package() throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        // given
        final Package aPackage = DefaultFieldValueChanger.INSTANCE.getClass()
                                                                  .getPackage();

        // when
        final Set<Class> result = countFieldValueChangersByComposition();
        final Set<Class> expectedResult = getClassesForPackage(aPackage.getName());

        // then
        assertThat(result).containsOnly(expectedResult.toArray(new Class[]{}));
        assertThat(result.size()).isEqualTo(expectedResult.size());
    }

    @Test
    void Should_Not_Contain_All_Field_Value_Changers_From_Package() throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        // given
        final Package aPackage = DefaultDateAndTimeFieldValueChanger.INSTANCE.getClass()
                                                                             .getPackage();

        // when
        final Set<Class> result = countFieldValueChangersByComposition();
        final Set<Class> expectedResult = getClassesForPackage(aPackage.getName());

        // then
        assertThat(result.size()).isNotEqualTo(expectedResult.size());
    }

    /**
     * http://stackoverflow.com/a/39006103/3640655
     */
    private Set<Class> getClassesForPackage(final String pckgname) throws ClassNotFoundException {
        // This will hold a list of directories matching the pckgname. There may be more than one if a package is split over multiple jars/paths
        final ArrayList<File> directories = new ArrayList<>();
        final String packageToPath = pckgname.replace('.', '/');
        try {
            final ClassLoader cld = Thread.currentThread()
                                          .getContextClassLoader();
            if (cld == null) {
                throw new ClassNotFoundException("Can't get class loader.");
            }

            // Ask for all resources for the packageToPath
            final Enumeration<URL> resources = cld.getResources(packageToPath);
            while (resources.hasMoreElements()) {
                directories.add(new File(URLDecoder.decode(resources.nextElement()
                                                                    .getPath(), "UTF-8")));
            }
        } catch (final NullPointerException x) {
            throw new ClassNotFoundException(pckgname +
                                                     " does not appear to be a valid package (Null pointer exception)");
        } catch (final UnsupportedEncodingException encex) {
            throw new ClassNotFoundException(pckgname +
                                                     " does not appear to be a valid package (Unsupported encoding)");
        } catch (final IOException ioex) {
            throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + pckgname);
        }

        final ArrayList<Class> classes = new ArrayList<>();
        // For every directoryFile identified capture all the .class files
        while (!directories.isEmpty()) {
            final File directoryFile = directories.remove(0);
            if (directoryFile.exists()) {
                // Get the list of the files contained in the package
                final File[] files = directoryFile.listFiles();

                for (final File file : files) {
                    // we are only interested in .class files
                    if ((file.getName()
                             .endsWith(".class")) && (!file.getName()
                                                           .contains("$"))) {
                        // removes the .class extension
                        final int index = directoryFile.getPath()
                                                       .indexOf(packageToPath);
                        final String packagePrefix = directoryFile.getPath()
                                                                  .substring(index)
                                                                  .replace('/', '.');
                        try {
                            final String className = packagePrefix +
                                    '.' +
                                    file.getName()
                                        .substring(0,
                                                   file.getName()
                                                       .length() - 6);
                            classes.add(Class.forName(className));
                        } catch (final NoClassDefFoundError e) {
                            // do nothing. this class hasn't been found by the loader, and we don't care.
                        }
                    } else if (file.isDirectory()) { // If we got to a subdirectory
                        directories.add(new File(file.getPath()));
                    }
                }
            } else {
                throw new ClassNotFoundException(pckgname +
                                                         " (" +
                                                         directoryFile.getPath() +
                                                         ") does not appear to be a valid package");
            }
        }
        return classes.stream()
                      .filter(AbstractFieldValueChanger.class::isAssignableFrom)
                      .filter(clazz -> !clazz.isAnonymousClass())
                      .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                      .collect(Collectors.toSet());
    }

    private Set<Class> countFieldValueChangersByComposition() throws NoSuchFieldException, IllegalAccessException {
        AbstractFieldValueChanger next = DefaultFieldValueChanger.INSTANCE;
        final Field field = AbstractFieldValueChanger.class.getDeclaredField("next");
        field.setAccessible(true);

        final Set<Class> changers = new HashSet<>();
        changers.add(next.getClass());

        do {
            next = (AbstractFieldValueChanger) field.get(next);
            if (next != null) {
                changers.add(next.getClass());
            } else {
                break;
            }
        } while (true);
        return changers;
    }
}
