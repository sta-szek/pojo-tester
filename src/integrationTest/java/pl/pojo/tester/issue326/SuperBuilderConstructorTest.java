package pl.pojo.tester.issue326;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

class SuperBuilderConstructorTest {

    @Test
    void Should_Test_A_Class_That_Also_Has_A_Protected_Builder_Constructor() {
        assertPojoMethodsFor(Order.class).areWellImplemented();
    }
}
