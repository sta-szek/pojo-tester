package pl.pojo.tester.issue248;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.AbstractAssertion;
import pl.pojo.tester.internal.assertion.equals.AbstractEqualsAssertionError;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;

class BrokenEqualsTest {

    @Test
    void Should_Reject_Equals_That_Returns_False_For_Equal_Values() {
        final AbstractAssertion assertion = assertPojoMethodsFor(BrokenEquals.class).testing(EQUALS);

        assertThrows(AbstractEqualsAssertionError.class, assertion::areWellImplemented);
    }
}
