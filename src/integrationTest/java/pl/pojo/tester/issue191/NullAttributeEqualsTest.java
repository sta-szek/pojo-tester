package pl.pojo.tester.issue191;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class NullAttributeEqualsTest {

    @Test
    void Should_Compare_A_Null_Attribute() {
        NamedEntity.bothNull = 0;

        assertPojoMethodsFor(NamedEntity.class).testing(EQUALS, HASH_CODE).areWellImplemented();

        assertTrue(NamedEntity.bothNull > 0);
    }
}
