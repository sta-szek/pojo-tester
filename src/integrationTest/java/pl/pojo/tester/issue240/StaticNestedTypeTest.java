package pl.pojo.tester.issue240;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class StaticNestedTypeTest {

    @Test
    void Should_Test_A_Class_That_Holds_A_Static_Nested_Type() {
        assertPojoMethodsFor(Questionnaire.class).testing(EQUALS, HASH_CODE).areWellImplemented();
    }

    @Test
    void Should_Test_The_Nested_Type_On_Its_Own() {
        assertPojoMethodsFor(Questionnaire.Answer.class).testing(EQUALS, HASH_CODE).areWellImplemented();
    }
}
