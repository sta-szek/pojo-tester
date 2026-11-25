package pl.pojo.tester.issue330;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

class ExceptionClassTest {

    @Test
    void Should_Test_An_Exception_Without_Reading_Throwable_Fields() {
        assertPojoMethodsFor(UnknownIdException.class).areWellImplemented();
    }
}
