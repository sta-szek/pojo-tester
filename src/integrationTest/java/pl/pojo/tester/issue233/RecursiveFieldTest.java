package pl.pojo.tester.issue233;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.GETTER;

class RecursiveFieldTest {

    @Test
    void Should_Instantiate_A_Field_Of_The_Same_Type() {
        assertPojoMethodsFor(Pit.class).testing(GETTER).areWellImplemented();
    }
}
