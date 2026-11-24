package pl.pojo.tester.issue210;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.AbstractAssertion;
import pl.pojo.tester.internal.utils.GetterNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.GETTER;
import static pl.pojo.tester.api.assertion.Method.SETTER;

class FieldPrefixRegressionTest {

    @Test
    void Should_Match_Accessors_After_The_Prefix() {
        assertPojoMethodsFor(PrefixedName.class).testing(GETTER, SETTER).withFieldPrefix("m").areWellImplemented();
    }

    @Test
    void Should_Fail_When_The_Prefix_Is_Not_Applied() {
        final AbstractAssertion assertion = assertPojoMethodsFor(PrefixedName.class).testing(GETTER);

        assertThrows(GetterNotFoundException.class, assertion::areWellImplemented);
    }
}
