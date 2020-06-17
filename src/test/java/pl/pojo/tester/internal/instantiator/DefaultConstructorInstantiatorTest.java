package pl.pojo.tester.internal.instantiator;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


class DefaultConstructorInstantiatorTest {

    @Test
    void Should_Create_Object_Using_Default_Constructor() {
        // given
        final Class<String> classToInstantiate = String.class;
        final DefaultConstructorInstantiator instantiator = new DefaultConstructorInstantiator(classToInstantiate);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(classToInstantiate);
    }

    @Test
    void Should_Throw_Exception_When_Cannot_Instantiate_Object() {
        // given
        final Class<?> classToInstantiate = No_Args_Constructor_Throws_IllegalAccessException.class;
        final DefaultConstructorInstantiator instantiator = new DefaultConstructorInstantiator(classToInstantiate);

        // when
        final Throwable result = catchThrowable(instantiator::instantiate);

        // then
        assertThat(result).isInstanceOf(ObjectInstantiationException.class);
    }

    private class No_Args_Constructor_Throws_IllegalAccessException {

        No_Args_Constructor_Throws_IllegalAccessException() throws IllegalAccessException {
            throw new IllegalAccessException("test");
        }
    }

}
