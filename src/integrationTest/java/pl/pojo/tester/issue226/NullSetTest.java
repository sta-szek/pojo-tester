package pl.pojo.tester.issue226;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class NullSetTest {

    @Test
    void Should_Tell_A_Null_Set_From_An_Empty_Set() {
        assertPojoMethodsFor(User.class).testing(EQUALS, HASH_CODE).areWellImplemented();
    }
}
