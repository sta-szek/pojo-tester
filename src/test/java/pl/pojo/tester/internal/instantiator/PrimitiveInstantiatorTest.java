package pl.pojo.tester.internal.instantiator;

import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class PrimitiveInstantiatorTest {

    @TestFactory
    public Stream<DynamicTest> Should_Instantiate_Primitive() {
        return Stream.of(Integer.class,
                         Byte.class,
                         Character.class,
                         Double.class,
                         Float.class,
                         Integer.class,
                         Long.class,
                         Short.class,
                         boolean.class,
                         byte.class,
                         char.class,
                         double.class,
                         float.class,
                         int.class,
                         long.class,
                         short.class)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Instantiate_Primitive(value)));
    }

    public Executable Should_Instantiate_Primitive(final Class<?> classToInstantiate) {
        return () -> {
            // given
            final PrimitiveInstantiator instantiator = new PrimitiveInstantiator(classToInstantiate);

            // when
            final Object result = instantiator.instantiate();

            // then
            assertThat(result).isNotNull();
        };
    }
}
