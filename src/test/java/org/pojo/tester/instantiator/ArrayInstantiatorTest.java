package org.pojo.tester.instantiator;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ArrayInstantiatorTest {

    @Test
    @Parameters(method = "arrayClassesToInstantiate")
    public void Should_Create_Array(final Class<?> classToInstantiate) {
        // given
        final ArrayInstantiator instantiator = new ArrayInstantiator(classToInstantiate);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(classToInstantiate);
    }

    private Object[] arrayClassesToInstantiate() {
        return new Object[]{Boolean[].class,
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
                            short[].class
        };
    }
}
