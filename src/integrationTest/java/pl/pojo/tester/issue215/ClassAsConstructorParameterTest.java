package pl.pojo.tester.issue215;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.ConstructorParameters;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.*;

class ClassAsConstructorParameterTest {

    @Test
    void Should_Test_Class_With_Class_As_Constructor_Parameter() {
        // given
        final Class<?> classUnderTest = ProductConfig.class;
        final Object[] parameters = {Nourriture.class, "Legume", 1d, false};
        final Class<?>[] parameterTypes = {Class.class, String.class, double.class, boolean.class};
        final ConstructorParameters constructorParameters = new ConstructorParameters(parameters, parameterTypes);

        // when

        // then
        assertPojoMethodsFor(classUnderTest).create(classUnderTest, constructorParameters)
                                            .testing(GETTER)
                                            .testing(TO_STRING)
                                            .testing(EQUALS, HASH_CODE)
                                            .testing(CONSTRUCTOR)
                                            .areWellImplemented();
    }
}
