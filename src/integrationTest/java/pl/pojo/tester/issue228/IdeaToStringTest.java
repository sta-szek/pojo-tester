package pl.pojo.tester.issue228;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

class IdeaToStringTest {

    @Test
    void Should_Accept_Quotes_Around_A_String() {
        assertPojoMethodsFor(Body.class).testing(TO_STRING).areWellImplemented();
    }
}
