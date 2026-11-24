package pl.pojo.tester.issue235;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

class ObjectArrayToStringTest {

    @Test
    void Should_Accept_Arrays_ToString() {
        assertPojoMethodsFor(SearchResult.class).testing(TO_STRING).areWellImplemented();
    }
}
