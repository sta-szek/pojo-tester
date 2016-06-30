package org.pojo.tester.instantiator;

import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.TestFactory;
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

    @TestFactory
    public Stream<DynamicTest> Should_Instantiate_Primitive_By_Qualified_Class_Name() {
        return Stream.of("java.lang.Boolean",
                         "java.lang.Byte",
                         "java.lang.Character",
                         "java.lang.Double",
                         "java.lang.Float",
                         "java.lang.Integer",
                         "java.lang.Long",
                         "java.lang.Short")
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Instantiate_Primitive_By_Qualified_Class_Name(value)));
    }

    private Executable Should_Instantiate_Primitive_By_Qualified_Class_Name(final String qualifiedClassName) {
        return () -> {

            // given
            final PrimitiveInstantiator instantiator = new PrimitiveInstantiator(qualifiedClassName);

            // when
            final Object object = instantiator.instantiate();
            final String result = object.getClass()
                                        .getCanonicalName();

            // then
            assertThat(result).isEqualTo(qualifiedClassName);
        };
    }

    private Executable Should_Instantiate_Primitive(final Class<?> classToInstantiate) {
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
