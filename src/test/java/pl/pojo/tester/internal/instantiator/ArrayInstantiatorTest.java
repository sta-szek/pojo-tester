package pl.pojo.tester.internal.instantiator;


import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


class ArrayInstantiatorTest {

    @TestFactory
    Stream<DynamicTest> Should_Create_Array_By_Class() {
        return Stream.of(Integer[].class,
                         Byte[].class,
                         Character[].class,
                         Double[].class,
                         Float[].class,
                         Integer[].class,
                         Long[].class,
                         Short[].class,
                         boolean[].class,
                         byte[].class,
                         char[].class,
                         double[].class,
                         float[].class,
                         int[].class,
                         long[].class,
                         short[].class)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Create_Array(value)));
    }

    private Executable Should_Create_Array(final Class<?> classToInstantiate) {
        return () -> {
            // given
            final ArrayInstantiator instantiator = new ArrayInstantiator(classToInstantiate);

            // when
            final Object result = instantiator.instantiate();

            // then
            assertThat(result).isInstanceOf(classToInstantiate);
        };
    }


}
