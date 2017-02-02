package pl.pojo.tester.internal.assertion.constructor;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


public class ConstructorAssertionsTest {

    @Test
    public void Should_Not_Throw_Exception_When_One_Arg_Constructor_Initialized_Class() throws NoSuchMethodException {
        // given
        final Constructor<?> declaredConstructor = StringConstructor.class.getDeclaredConstructor(String.class);
        final ConstructorAssertions assertions = new ConstructorAssertions(declaredConstructor);
        final Object[] constructorParameters = {"string"};

        // when
        final Throwable result = catchThrowable(() -> assertions.willInstantiateClassUsing(constructorParameters));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Not_Throw_Exception_When_Default_Constructor_Initialized_Class() throws NoSuchMethodException {
        // given
        final Constructor<?> declaredConstructor = DefaultConstructor.class.getDeclaredConstructor();
        final ConstructorAssertions assertions = new ConstructorAssertions(declaredConstructor);
        final Object[] constructorParameters = null;

        // when
        final Throwable result = catchThrowable(() -> assertions.willInstantiateClassUsing(constructorParameters));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Throw_Exception_When_Getter_Does_Not_Return_Expected_Value() throws NoSuchMethodException {
        // given
        final Constructor<?> declaredConstructor = ConstructorThrowingException.class.getDeclaredConstructor();
        final ConstructorAssertions assertions = new ConstructorAssertions(declaredConstructor);
        final Object[] constructorParameters = null;

        // when
        final Throwable result = catchThrowable(() -> assertions.willInstantiateClassUsing(constructorParameters));

        // then
        assertThat(result).isInstanceOf(ConstructorAssertionError.class);
    }

    private static class StringConstructor {
        public StringConstructor(final String string) {
        }
    }

    private static class DefaultConstructor {

    }

    private static class ConstructorThrowingException {
        public ConstructorThrowingException() {
            throw new RuntimeException();
        }
    }

}