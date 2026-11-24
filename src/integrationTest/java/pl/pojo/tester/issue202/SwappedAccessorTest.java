package pl.pojo.tester.issue202;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.AbstractAssertion;
import pl.pojo.tester.internal.assertion.AbstractAssertionError;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.SETTER;

class SwappedAccessorTest {

    @Test
    void Should_Fail_When_A_Getter_Returns_Another_Field() {
        final AbstractAssertion assertion = assertPojoMethodsFor(BadGetter.class).testing(GETTER);

        assertThrows(AbstractAssertionError.class, assertion::areWellImplemented);
    }

    @Test
    void Should_Fail_When_A_Setter_Copies_Another_Field() {
        final AbstractAssertion assertion = assertPojoMethodsFor(BadSetter.class).testing(SETTER);

        assertThrows(AbstractAssertionError.class, assertion::areWellImplemented);
    }
}
