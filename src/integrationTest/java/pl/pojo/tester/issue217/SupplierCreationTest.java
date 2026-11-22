package pl.pojo.tester.issue217;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Method.EQUALS;
import static pl.pojo.tester.api.assertion.Method.HASH_CODE;
import static pl.pojo.tester.api.assertion.Method.TO_STRING;

class SupplierCreationTest {

    @Test
    void Should_Instantiate_Dependency_That_Validates_Constructor_Arguments() {
        assertPojoMethodsFor(Order.class)
                .create(ValidatedCode.class, () -> new ValidatedCode("accepted"))
                .testing(EQUALS, HASH_CODE, TO_STRING)
                .areWellImplemented();
    }

    @Test
    void Should_Instantiate_Class_From_Its_Builder() {
        assertPojoMethodsFor(Built.class)
                .create(Built.class, () -> Built.builder().name("n").build())
                .testing(EQUALS, HASH_CODE, TO_STRING)
                .areWellImplemented();
    }
}
