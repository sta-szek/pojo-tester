package pl.pojo.tester.usecase.types;


import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class AllTypesTest {

    @Test
    public void Should_Test_Class_With_All_Field_Types() {
        // given

        // when

        // then
        assertPojoMethodsFor(ClassWithAllFieldTypes.class).areWellImplemented();
    }
}
