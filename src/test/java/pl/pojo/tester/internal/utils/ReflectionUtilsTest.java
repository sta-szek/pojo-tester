package pl.pojo.tester.internal.utils;

import classesForTest.reflectionUtils.next.D;
import classesForTest.reflectionUtils.next.E;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
}