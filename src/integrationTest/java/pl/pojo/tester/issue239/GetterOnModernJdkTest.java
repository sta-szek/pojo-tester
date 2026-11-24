package pl.pojo.tester.issue239;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.GETTER;

class GetterOnModernJdkTest {

    @Test
    void Should_Read_A_Field_Without_Field_Modifiers() {
        assertPojoMethodsFor(Named.class).testing(GETTER).areWellImplemented();
    }
}
