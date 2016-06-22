package org.pojo.tester.instantiator;


import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.TestFactory;
import test.instantiator.abstracts.Abstract;
import test.instantiator.abstracts.Abstract_PrivateConstructor;
import test.instantiator.abstracts.Annotation;
import test.instantiator.abstracts.Interface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;

public class ProxyInstantiatorTest {

    @TestFactory
    public Stream<DynamicTest> Should_Instantiate_Abstract_Interface_Or_Annotation_Classes() {
        return Stream.of(Annotation.class, Abstract.class, Interface.class, Abstract_PrivateConstructor.class)
                     .map(value -> dynamicTest(getDefaultDisplayName(value.getName()),
                                               Should_Instantiate_Abstract_Interface_Or_Annotation_Classes(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Instantiate_Abstract_Interface_Or_Annotation_Classes_By_Qualified_Class_Names() {
        return Stream.of("test.instantiator.abstracts.Annotation",
                         "test.instantiator.abstracts.Abstract_PrivateConstructor",
                         "test.instantiator.abstracts.Abstract",
                         "test.instantiator.abstracts.Interface")
                     .map(value -> dynamicTest(getDefaultDisplayName(value),
                                               Should_Instantiate_Abstract_Interface_Or_Annotation_Classes_By_Qualified_Class_Names
                                                       (value)));
    }

    private Executable Should_Instantiate_Abstract_Interface_Or_Annotation_Classes_By_Qualified_Class_Names(final String qualifiedName) {
        return () -> {
            // given
            final ProxyInstantiator instantiator = new ProxyInstantiator(qualifiedName);

            // when
            final Object object = instantiator.instantiate();

            // then
            assertThat(object).isNotNull();
        };
    }

    private Executable Should_Instantiate_Abstract_Interface_Or_Annotation_Classes(final Class<?> classToInstantiate) {
        return () -> {
            // given
            final ProxyInstantiator instantiator = new ProxyInstantiator(classToInstantiate);

            // when
            final Object result = instantiator.instantiate();

            // then
            assertThat(result).isInstanceOf(classToInstantiate);
        };
    }

}
