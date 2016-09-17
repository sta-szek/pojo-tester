package pl.pojo.tester.internal.instantiator;


import classesForTest.instantiator.abstracts.Abstract;
import classesForTest.instantiator.abstracts.Abstract_PrivateConstructor;
import classesForTest.instantiator.abstracts.Annotation;
import classesForTest.instantiator.abstracts.Interface;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static classesForTest.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@RunWith(JUnitPlatform.class)
public class ProxyInstantiatorTest {

    @TestFactory
    public Stream<DynamicTest> Should_Instantiate_Abstract_Interface_Or_Annotation_Classes() {
        return Stream.of(Annotation.class, Abstract.class, Interface.class, Abstract_PrivateConstructor.class)
                     .map(value -> dynamicTest(getDefaultDisplayName(value.getName()),
                                               Should_Instantiate_Abstract_Interface_Or_Annotation_Classes(value)));
    }

    public Executable Should_Instantiate_Abstract_Interface_Or_Annotation_Classes(final Class<?> classToInstantiate) {
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
