package pl.pojo.tester.issue237;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class FinalFieldDefaultPathTest {

    @Test
    void Should_Not_Touch_Field_Modifiers() {
        assertPojoMethodsFor(WithFinal.class).testing(EQUALS, HASH_CODE).areWellImplemented();
    }
}
