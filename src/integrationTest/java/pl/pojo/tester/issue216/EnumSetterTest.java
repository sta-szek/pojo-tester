package pl.pojo.tester.issue216;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.SETTER;

class EnumSetterTest {

    @Test
    void Should_Skip_Setters_On_An_Enum() {
        assertPojoMethodsFor(Status.class).testing(SETTER).areWellImplemented();
    }
}
