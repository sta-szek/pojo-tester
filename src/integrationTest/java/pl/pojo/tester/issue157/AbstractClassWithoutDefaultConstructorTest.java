package pl.pojo.tester.issue157;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class AbstractClassWithoutDefaultConstructorTest {

    @Test
    public void shouldPojoBeWellImplemented() {
        assertPojoMethodsFor(AbstractClass.class).areWellImplemented();
    }
}