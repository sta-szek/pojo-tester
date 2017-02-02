package pl.pojo.tester.usecase.types;


import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class AllTypesTest {

    @Test
    public void Should_Test_Class_With_Collections_Collection_Types() {
        // given

        // when

        // then
        assertPojoMethodsFor(Collections_Collection_Types.class).areWellImplemented();
    }

    @Test
    public void Should_Test_Class_With_Collections_Iterators_Types() {
        // given

        // when

        // then
        assertPojoMethodsFor(Collections_Iterators_Types.class).areWellImplemented();
    }

    @Test
    public void Should_Test_Class_With_Collections_Map_Types() {
        // given

        // when

        // then
        assertPojoMethodsFor(Collections_Map_Types.class).areWellImplemented();
    }

    @Test
    public void Should_Test_Class_With_Collections_Other_Types() {
        // given

        // when

        // then
        assertPojoMethodsFor(Collections_Other_Types.class).areWellImplemented();
    }

    @Test
    public void Should_Test_Class_With_Collections_Types() {
        // given

        // when

        // then
        assertPojoMethodsFor(Collections_Types.class).areWellImplemented();
    }

    @Test
    public void Should_Test_Class_With_Math_Types() {
        // given

        // when

        // then
        assertPojoMethodsFor(Math_Types.class).areWellImplemented();
    }

    @Test
    public void Should_Test_Class_With_Other_Types() {
        // given

        // when

        // then
        assertPojoMethodsFor(Other_Types.class).areWellImplemented();
    }

    @Test
    public void Should_Test_Class_With_Primitive_Types() {
        // given

        // when

        // then
        assertPojoMethodsFor(Primitive_Types.class).areWellImplemented();
    }

    @Test
    public void Should_Test_Class_With_Primitive_Wrapped_Types() {
        // given

        // when

        // then
        assertPojoMethodsFor(Primitive_Wrapped_Types.class).areWellImplemented();
    }

}
