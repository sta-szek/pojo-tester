package org.pojo.tester.instantiator;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.instantiator.Abstract;
import test.instantiator.Annotation;
import test.instantiator.Interface;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ProxyInstantiatorTest {

    @Test
    @Parameters(method = "classes")
    public void Should_Instantiate_Abstract_Interface_Or_Annotation_Classes(final Class<?> classToInstantiate) {
        // given
        final ProxyInstantiator instantiator = new ProxyInstantiator(classToInstantiate);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(classToInstantiate);
    }

    private Object[] classes() {
        return new Object[]{Annotation.class,
                            Abstract.class,
                            Interface.class,
                            };
    }
}
