package pl.pojo.tester.internal.utils;

import classesForTest.reflectionUtils.next.D;
import classesForTest.reflectionUtils.next.E;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionUtilsTest {

    @Test
    void Should_Return_Classes_From_Given_Package_Name() throws IOException {
        // given
        final Class<?>[] expectedClasses = new Class[]{ D.class, E.class };

        // when
        final Class<?>[] result = ReflectionUtils.getClassesFromPackage("classesForTest.reflectionUtils.next");

        // then
        assertThat(result).containsExactlyInAnyOrder(expectedClasses);
    }

    @Test
    void Should_Ignore_Files_That_Are_Not_Classes() throws IOException {
        final URL packageUrl = Thread.currentThread()
                                     .getContextClassLoader()
                                     .getResource("classesForTest/reflectionUtils/next");
        final Path xml = Paths.get(packageUrl.getPath(), "mapper.xml");
        Files.write(xml, "<mapper/>".getBytes());
        try {
            final Class<?>[] result = ReflectionUtils.getClassesFromPackage("classesForTest.reflectionUtils.next");

            assertThat(result).containsExactlyInAnyOrder(D.class, E.class);
        } finally {
            Files.deleteIfExists(xml);
        }
    }
}