package pl.pojo.tester.issue229;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.SETTER;

class BuilderSetterTest {

    @Test
    void Should_Accept_A_Setter_That_Returns_This() {
        assertPojoMethodsFor(FluentBody.class).testing(GETTER, SETTER).areWellImplemented();
    }
}
