package pl.pojo.tester.issue328;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

class CapitalizedFieldNameTest {

    @Test
    void Should_Accept_GetTRow() {
        assertPojoMethodsFor(SheetRow.class).areWellImplemented();
    }
}
