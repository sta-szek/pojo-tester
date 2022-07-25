package pl.pojo.tester.issue192;


import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

class ConstructorWithZonedDateTimeTest {

    @Test
    void Should_Create_Instance_That_Have_Zoned_Date_Time_As_Constructor_Parameter() {
        assertPojoMethodsFor(ConstructorWithZonedDateTime.class).areWellImplemented();
    }
}

