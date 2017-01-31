package pl.pojo.tester.issue153;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.*;

public class NullOrEmptyCollectionsTest {

    @Test
    public void shouldPojoBeWellImplemented() {
        assertPojoMethodsFor(Message.class).testing(TO_STRING, EQUALS, CONSTRUCTOR, HASH_CODE)
                                           .areWellImplemented();
    }
}