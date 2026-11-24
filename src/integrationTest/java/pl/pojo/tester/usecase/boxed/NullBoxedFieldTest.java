package pl.pojo.tester.usecase.boxed;

import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;

class NullBoxedFieldTest {

    @Test
    void Should_Test_Nested_Nullable_Boxed_Fields() {
        assertPojoMethodsForAll(Location.class, Reading.class).areWellImplemented();
    }
}
