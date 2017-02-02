package pl.pojo.tester.internal.assertion.constructor;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;


public class ConstructorAssertionErrorTest {

    @Test
    public void Should_Return_Expected_Detailed_Message_Without_Parameters() throws NoSuchMethodException {
        // given
        final String expectedMessage =
                "Constructor:\n" +
                "private pl.pojo.tester.internal.assertion.constructor.ConstructorAssertionErrorTest$Pojo()\n" +
                "of class:\n" +
                "class pl.pojo.tester.internal.assertion.constructor.ConstructorAssertionErrorTest$Pojo\n" +
                "could not create instance with parameters:\n" +
                "<no parameters>\n" +
                "Root cause is:\n" +
                "test";
        final Class<?> testedCass = Pojo.class;
        final Constructor<?> declaredConstructor = testedCass.getDeclaredConstructor();
        final Object[] constructorParameters = null;
        final ClassNotFoundException e = new ClassNotFoundException("test");
        final ConstructorAssertionError error = new ConstructorAssertionError(testedCass,
                                                                              declaredConstructor,
                                                                              constructorParameters,
                                                                              e);

        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }

    @Test
    public void Should_Return_Expected_Detailed_Message_With_Parameters() throws NoSuchMethodException {
        // given
        final String expectedMessage =
                "Constructor:\n" +
                "private pl.pojo.tester.internal.assertion.constructor.ConstructorAssertionErrorTest$Pojo()\n" +
                "of class:\n" +
                "class pl.pojo.tester.internal.assertion.constructor.ConstructorAssertionErrorTest$Pojo\n" +
                "could not create instance with parameters:\n" +
                "[ test1, 1, test2 ]\n" +
                "Root cause is:\n" +
                "test";
        final Class<?> testedCass = Pojo.class;
        final Constructor<?> declaredConstructor = testedCass.getDeclaredConstructor();
        final Object[] constructorParameters = new Object[]{"test1", 1, "test2"};
        final ClassNotFoundException e = new ClassNotFoundException("test");
        final ConstructorAssertionError error = new ConstructorAssertionError(testedCass,
                                                                              declaredConstructor,
                                                                              constructorParameters,
                                                                              e);

        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }

    @Test
    public void Should_Return_Expected_Error_Prefix() throws NoSuchMethodException {
        // given
        final String expectedMessage = "Class pl.pojo.tester.internal.assertion.constructor" +
                                       ".ConstructorAssertionErrorTest.Pojo has bad 'constructor' method " +
                                       "implementation.";

        final Class<?> testedCass = Pojo.class;
        final Constructor<?> declaredConstructor = testedCass.getDeclaredConstructor();
        final Object[] constructorParameters = null;
        final ClassNotFoundException e = new ClassNotFoundException();
        final ConstructorAssertionError error = new ConstructorAssertionError(testedCass,
                                                                              declaredConstructor,
                                                                              constructorParameters,
                                                                              e);
        // when
        final String result = error.getErrorPrefix();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }

    private static class Pojo {}
}