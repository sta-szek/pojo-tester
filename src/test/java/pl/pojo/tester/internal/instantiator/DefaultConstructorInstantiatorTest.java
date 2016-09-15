package pl.pojo.tester.internal.instantiator;


import classesForTest.instantiator.No_Args_Constructor_Throws_IllegalAccessException;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(JUnitPlatform.class)
public class DefaultConstructorInstantiatorTest {

    @Test
    public void Should_Create_Object_Using_Default_Constructor() {
        // given
        final Class<String> classToInstantiate = String.class;
        final DefaultConstructorInstantiator instantiator = new DefaultConstructorInstantiator(classToInstantiate);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(classToInstantiate);
    }

    @Test
    public void Should_Throw_Exception_When_Cannot_Instantiate_Object() {
        // given
        final Class<No_Args_Constructor_Throws_IllegalAccessException> classToInstantiate =
                No_Args_Constructor_Throws_IllegalAccessException.class;
        final DefaultConstructorInstantiator instantiator = new DefaultConstructorInstantiator(classToInstantiate);

        // when
        final Throwable result = catchThrowable(instantiator::instantiate);

        // then
        assertThat(result).isInstanceOf(ObjectInstantiationException.class);
    }

}
