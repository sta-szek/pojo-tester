package pl.pojo.tester.internal.instantiator;


import classesForTest.Abstract;
import classesForTest.Abstract_PrivateConstructor;
import classesForTest.Annotation;
import classesForTest.Interface;
import lombok.Data;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.LinkedList;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


class ProxyInstantiatorTest {


    @TestFactory
    Stream<DynamicTest> Should_Instantiate_Abstract_Interface_Or_Annotation_Classes() {
        return Stream.of(Annotation.class, Abstract.class, Interface.class, Abstract_PrivateConstructor.class)
                     .map(value -> dynamicTest(getDefaultDisplayName(value.getName()),
                                               Should_Instantiate_Abstract_Interface_Or_Annotation_Classes(value)));
    }

    private Executable Should_Instantiate_Abstract_Interface_Or_Annotation_Classes(final Class<?> classToInstantiate) {
        return () -> {
            // given
            final ProxyInstantiator instantiator = new ProxyInstantiator(classToInstantiate, new LinkedList<>());

            // when
            final Object result = instantiator.instantiate();

            // then
            assertThat(result).isInstanceOf(classToInstantiate);
        };
    }

    @Test
    void Should_Create_Java_Proxy_Which_Returns_Expected_Values() {
        // given
        final ProxyInstantiator instantiator = new ProxyInstantiator(Interface.class, new LinkedList<>());

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result.toString()).isEqualTo("string");
        assertThat(result.equals(null)).isTrue();
        assertThat(result.hashCode()).isZero();
    }

    @TestFactory
    Stream<DynamicTest> Should_Create_Abstract_Class_Without_Default_Constructor() {
        return Stream.of(A.class, B.class, C.class, D.class, E.class)
                     .map(value -> dynamicTest(getDefaultDisplayName(value.getName()),
                                               Should_Create_Abstract_Class_Without_Default_Constructor(value)));
    }

    private Executable Should_Create_Abstract_Class_Without_Default_Constructor(final Class<?> classToInstantiate) {
        return () -> {
            // given

            final ProxyInstantiator instantiator = new ProxyInstantiator(classToInstantiate, new LinkedList<>());

            // when
            final Object result = instantiator.instantiate();

            // then
            assertThat(result).isInstanceOf(classToInstantiate);
        };
    }

    @Data
    static class A {
        private final int a;
    }

    @Data
    private static class B {
        private final int a;
    }

    @Data
    class C {
        private final int a;
    }

    @Data
    public class D {
        private final int a;
    }

    @Data
    private class E {
        private final int a;
    }

}
