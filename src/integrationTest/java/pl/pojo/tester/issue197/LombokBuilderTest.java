package pl.pojo.tester.issue197;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.GETTER;

class LombokBuilderTest {

    @Test
    void Should_Test_A_Class_Created_From_A_Lombok_Builder() {
        assertPojoMethodsFor(BuiltEntity.class)
                .create(BuiltEntity.class, () -> BuiltEntity.builder().name("n").build())
                .testing(GETTER)
                .areWellImplemented();
    }
}
