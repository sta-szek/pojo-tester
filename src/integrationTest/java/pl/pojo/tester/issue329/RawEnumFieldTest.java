package pl.pojo.tester.issue329;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

class RawEnumFieldTest {

    @Test
    void Should_Test_A_Class_With_A_Raw_Enum_Field() {
        assertPojoMethodsFor(SkillChange.class).areWellImplemented();
    }
}
