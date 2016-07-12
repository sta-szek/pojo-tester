package org.pojo.tester.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.pojo.tester.GetterNotFoundException;
import org.pojo.tester.SetterNotFoundException;
import test.fields.Getters;
import test.fields.Setters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class MethodUtilsTest {

    @TestFactory
    public Stream<DynamicTest> Should_Throw_Exception_When_Setter_Was_Not_Found() throws NoSuchFieldException {
        final Field fieldA = fieldFromSettersClass("a");
        final Field fieldB = fieldFromSettersClass("b");
        final Field fieldC = fieldFromSettersClass("c");
        final Field fieldD = fieldFromSettersClass("d");
        final Field fieldF = fieldFromSettersClass("f");
        final Field fieldG = fieldFromSettersClass("g");

        return Stream.of(fieldA,
                         fieldB,
                         fieldC,
                         fieldD,
                         fieldF,
                         fieldG)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Throw_Exception_When_Setter_Was_Not_Found(value)));
    }

    public Executable Should_Throw_Exception_When_Setter_Was_Not_Found(final Field field) {
        return () -> {
            // when
            final Throwable result = catchThrowable(() -> MethodUtils.findSetterFor(Setters.class, field));

            // then
            assertThat(result).isInstanceOf(SetterNotFoundException.class);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Throw_Exception_When_Getter_Was_Not_Found() throws NoSuchFieldException {
        final Field fieldA = fieldFromGettersClass("a");
        final Field fieldB = fieldFromGettersClass("b");
        final Field fieldD = fieldFromGettersClass("d");
        final Field fieldE = fieldFromGettersClass("e");
        final Field fieldF = fieldFromGettersClass("f");
        final Field fieldG = fieldFromGettersClass("g");

        return Stream.of(fieldA,
                         fieldB,
                         fieldD,
                         fieldE,
                         fieldF,
                         fieldG)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Throw_Exception_When_Getter_Was_Not_Found(value)));
    }

    public Executable Should_Throw_Exception_When_Getter_Was_Not_Found(final Field field) {
        return () -> {
            // when
            final Throwable result = catchThrowable(() -> MethodUtils.findGetterFor(Getters.class, field));

            // then
            assertThat(result).isInstanceOf(GetterNotFoundException.class);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Expected_Getter() throws NoSuchFieldException, NoSuchMethodException {
        final Field field1 = fieldFromGettersClass("getter1");
        final Field field2 = fieldFromGettersClass("getter2");
        final Field field3 = fieldFromGettersClass("getter3");
        final Field field4 = fieldFromGettersClass("getter4");
        final Field field5 = fieldFromGettersClass("getter5");
        final Field field6 = fieldFromGettersClass("getter6");
        final Field field7 = fieldFromGettersClass("getter7");

        return Stream.of(new GetterSetterTestCase(field1, Getters.class.getMethod("isGetter1")),
                         new GetterSetterTestCase(field2, Getters.class.getMethod("hasGetter2")),
                         new GetterSetterTestCase(field3, Getters.class.getMethod("haveGetter3")),
                         new GetterSetterTestCase(field4, Getters.class.getMethod("containsGetter4")),
                         new GetterSetterTestCase(field5, Getters.class.getMethod("getGetter5")),
                         new GetterSetterTestCase(field6, Getters.class.getMethod("getGetter6")),
                         new GetterSetterTestCase(field7, Getters.class.getMethod("getGetter7")))
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Return_Expected_Getter(value)));
    }

    public Executable Should_Return_Expected_Getter(final GetterSetterTestCase testCase) {
        return () -> {
            // when
            final Method result = MethodUtils.findGetterFor(Getters.class, testCase.field);

            // then
            assertThat(result).isEqualTo(testCase.expectedMethod);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_Expected_Setter() throws NoSuchFieldException, NoSuchMethodException {
        final Field field1 = fieldFromSettersClass("setter1");
        final Field field2 = fieldFromSettersClass("setter2");
        final Field field3 = fieldFromSettersClass("setter3");
        final Field field4 = fieldFromSettersClass("setter4");

        return Stream.of(new GetterSetterTestCase(field1, Setters.class.getMethod("setSetter1", int.class)),
                         new GetterSetterTestCase(field2, Setters.class.getMethod("setSetter2", Integer.class)),
                         new GetterSetterTestCase(field3, Setters.class.getMethod("setSetter3", Integer.class)),
                         new GetterSetterTestCase(field4, Setters.class.getMethod("setSetter4", int.class)))
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Return_Expected_Setter(value)));
    }

    public Executable Should_Return_Expected_Setter(final GetterSetterTestCase testCase) {
        return () -> {
            // when
            final Method result = MethodUtils.findSetterFor(Setters.class, testCase.field);

            // then
            assertThat(result).isEqualTo(testCase.expectedMethod);
        };
    }

    private Field fieldFromSettersClass(final String name) throws NoSuchFieldException {
        return Setters.class.getDeclaredField(name);
    }

    private Field fieldFromGettersClass(final String name) throws NoSuchFieldException {
        return Getters.class.getDeclaredField(name);
    }


    @AllArgsConstructor
    @ToString
    private class GetterSetterTestCase {
        private Field field;
        private Method expectedMethod;
    }
}
