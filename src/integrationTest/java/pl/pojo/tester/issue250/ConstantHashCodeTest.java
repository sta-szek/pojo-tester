package pl.pojo.tester.issue250;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.AbstractAssertion;
import pl.pojo.tester.internal.assertion.hashcode.NotEqualHashCodeAssertionError;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;

class ConstantHashCodeTest {

    @Test
    void Should_Reject_A_Constant_Hash_Code_By_Default() {
        final AbstractAssertion assertion = assertPojoMethodsFor(ConstantHash.class).testing(HASH_CODE);

        assertThrows(NotEqualHashCodeAssertionError.class, assertion::areWellImplemented);
    }

    @Test
    void Should_Allow_A_Constant_Hash_Code_When_Asked() {
        assertPojoMethodsFor(ConstantHash.class)
                .testing(HASH_CODE)
                .allowingSameHashCodeForUnequalObjects()
                .areWellImplemented();
    }
}
