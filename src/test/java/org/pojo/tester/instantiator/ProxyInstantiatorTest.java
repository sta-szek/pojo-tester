package org.pojo.tester.instantiator;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import test.instantiator.abstracts.Abstract;
import test.instantiator.abstracts.Abstract_PrivateConstructor;
import test.instantiator.abstracts.Annotation;
import test.instantiator.abstracts.Interface;

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

    @Test
    @Parameters(method = "classesNames")
    public void Should_Instantiate_Abstract_Interface_Or_Annotation_Classes_By_Qualified_Class_Names(final String qualifiedClassName) {
        // given
        final ProxyInstantiator instantiator = new ProxyInstantiator(qualifiedClassName);

        // when
        final Object object = instantiator.instantiate();

        // then
        assertThat(object).isNotNull();
    }

    private Object[] classes() {
        return new Object[]{Annotation.class,
                            Abstract.class,
                            Interface.class,
                            Abstract_PrivateConstructor.class,
                            };
    }

    private Object[] classesNames() {
        return new Object[]{"test.instantiator.abstracts.Annotation",
                            "test.instantiator.abstracts.Abstract_PrivateConstructor",
                            "test.instantiator.abstracts.Abstract",
                            "test.instantiator.abstracts.Interface",
                            };
    }
}
