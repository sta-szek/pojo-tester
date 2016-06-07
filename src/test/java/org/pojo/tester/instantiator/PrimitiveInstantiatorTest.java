package org.pojo.tester.instantiator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class PrimitiveInstantiatorTest {

    @Test
    @Parameters(method = "primitives")
    public void Should_Instantiate_Primitive(final Class<?> classToInstantiate) {
        // given
        final PrimitiveInstantiator instantiator = new PrimitiveInstantiator(classToInstantiate);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isNotNull();
    }

    @Test
    @Parameters(method = "primitivesNames")
    public void Should_Instantiate_Primitive_By_Qualified_Class_Name(final String qualifiedClassName) {
        // given
        final PrimitiveInstantiator instantiator = new PrimitiveInstantiator(qualifiedClassName);

        // when
        final Object object = instantiator.instantiate();
        final String result = object.getClass()
                                    .getCanonicalName();

        // then
        assertThat(result).isEqualTo(qualifiedClassName);
    }

    private Object[] primitives() {
        return new Object[]{Boolean.class,
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
                            short.class
        };
    }

    private Object[] primitivesNames() {
        return new Object[]{"java.lang.Boolean",
                            "java.lang.Byte",
                            "java.lang.Character",
                            "java.lang.Double",
                            "java.lang.Float",
                            "java.lang.Integer",
                            "java.lang.Long",
                            "java.lang.Short",
                            };
    }
}
