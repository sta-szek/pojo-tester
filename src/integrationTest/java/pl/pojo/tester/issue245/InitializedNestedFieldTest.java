package pl.pojo.tester.issue245;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class InitializedNestedFieldTest {

    @Test
    void Should_Accept_A_Nested_Field_Initialized_At_Declaration() {
        assertPojoMethodsFor(EntityUnderTest.class).testing(EQUALS, HASH_CODE).areWellImplemented();
    }
}
