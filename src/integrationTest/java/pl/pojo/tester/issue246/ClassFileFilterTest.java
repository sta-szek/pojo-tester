package pl.pojo.tester.issue246;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.DefaultPackageFilter;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassFileFilterTest {

    @Test
    void Should_Ignore_Files_That_Are_Not_Classes() throws Exception {
        final URL packageUrl = Thread.currentThread().getContextClassLoader().getResource("pl/pojo/tester/issue246");
        final Path xml = Paths.get(packageUrl.toURI()).resolve("mapper.xml");
        Files.write(xml, "<mapper/>".getBytes());
        try {
            final Class<?>[] classes = DefaultPackageFilter.forPackage("pl.pojo.tester.issue246").getClasses();

            assertTrue(Arrays.asList(classes).contains(Sample.class));
            assertFalse(Arrays.stream(classes).anyMatch(type -> type.getSimpleName().contains("mapper")));
        } finally {
            Files.deleteIfExists(xml);
        }
    }
}
