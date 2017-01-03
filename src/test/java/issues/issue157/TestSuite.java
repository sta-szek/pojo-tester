package issues.issue157;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class TestSuite {

    @Test
    public void shouldPojoBeWellImplemented() {
        assertPojoMethodsFor(AbstractClass.class).areWellImplemented();
    }
}