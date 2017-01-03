package pl.pojo.tester.internal.instantiator;


import classesForTest.Abstract;
import classesForTest.Abstract_PrivateConstructor;
import classesForTest.Annotation;
import classesForTest.Interface;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import pl.pojo.tester.api.ConstructorParameters;

import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


public class ProxyInstantiatorTest {

    private final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters = new ArrayListValuedHashMap<>();

    @TestFactory
    public Stream<DynamicTest> Should_Instantiate_Abstract_Interface_Or_Annotation_Classes() {
        return Stream.of(Annotation.class, Abstract.class, Interface.class, Abstract_PrivateConstructor.class)
                     .map(value -> dynamicTest(getDefaultDisplayName(value.getName()),
                                               Should_Instantiate_Abstract_Interface_Or_Annotation_Classes(value)));
    }

    public Executable Should_Instantiate_Abstract_Interface_Or_Annotation_Classes(final Class<?> classToInstantiate) {
        return () -> {
            // given
            final ProxyInstantiator instantiator = new ProxyInstantiator(classToInstantiate, constructorParameters);

            // when
            final Object result = instantiator.instantiate();

            // then
            assertThat(result).isInstanceOf(classToInstantiate);
        };
    }

    @Test
    public void Should_Create_Java_Proxy_Which_Returns_Expected_Values() {
        // given
        final ProxyInstantiator instantiator = new ProxyInstantiator(Interface.class, constructorParameters);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result.toString()).isEqualTo("string");
        assertThat(result.equals(null)).isTrue();
        assertThat(result.hashCode()).isZero();
    }


}
