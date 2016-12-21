package issues.issue153;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class TestSuite {

    @Test
    public void shouldPojoBeWellImplemented() {
        assertPojoMethodsFor(Message.class).testing(Method.TO_STRING,
                                                    Method.EQUALS,
                                                    Method.CONSTRUCTOR,
                                                    Method.HASH_CODE)
                                           .areWellImplemented();
    }
}