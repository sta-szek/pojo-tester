package pl.pojo.tester.issue251;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.SETTER;

class SetterOnModernJdkTest {

    @Test
    void Should_Write_A_Field_Without_Field_Modifiers() {
        assertPojoMethodsFor(Named.class).testing(SETTER).areWellImplemented();
    }
}
