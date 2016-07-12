package org.pojo.tester.field;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.pojo.tester.GetterNotFoundException;
import org.pojo.tester.SetterNotFoundException;
import test.TestHelper;
import test.fields.ClassWithAllAvailableFieldModifiers;
import test.fields.Getters;
import test.fields.Permutation1;
import test.fields.Permutation2;
import test.fields.Setters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class FieldUtilsTest {

    @Test
    public void Should_Return_All_Fields() {
        // given
        final List<Field> expectedFields = TestHelper.getAllFieldsExceptDummyJacocoField(ClassWithAllAvailableFieldModifiers.class);

        // when
        final List<Field> result = FieldUtils.getAllFields(ClassWithAllAvailableFieldModifiers.class);

        // then
        assertThat(result).hasSize(16)
                          .containsExactlyElementsOf(expectedFields);
    }

    @Test
    public void Should_Return_Specified_Fields() throws java.lang.NoSuchFieldException {
        // given
        final String fieldName = "a";
        final Field expectedField = ClassWithAllAvailableFieldModifiers.class.getDeclaredField(fieldName);

        // when
        final List<Field> result = FieldUtils.getSpecifiedFields(ClassWithAllAvailableFieldModifiers.class, newArrayList(fieldName));

        // then
        assertThat(result).hasSize(1)
                          .containsExactly(expectedField);
    }

    @Test
    public void Should_Return_Fields_Except_Fields_With_Given_Names() {
        // given
        final String fieldName = "a";

        // when
        final List<Field> result = FieldUtils.getAllFieldsExcluding(ClassWithAllAvailableFieldModifiers.class, newArrayList(fieldName));

        // then
        assertThat(result).hasSize(15);
    }

    @Test
    public void Should_Return_All_Fields_Names() {
        // given
        final List<String> expectedFields = TestHelper.getAllFieldsExceptDummyJacocoField(ClassWithAllAvailableFieldModifiers.class)
                                                      .stream()
                                                      .map(Field::getName)
                                                      .collect(Collectors.toList());

        // when
        final List<String> result = FieldUtils.getAllFieldNames(ClassWithAllAvailableFieldModifiers.class);

        // then
        assertThat(result).hasSize(16)
                          .containsExactlyElementsOf(expectedFields);
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_All_Permutations() throws NoSuchFieldException {
        final Field perm1A = fieldFromPermutation1Class("a");
        final Field perm1B = fieldFromPermutation1Class("b");
        final Field perm2A = fieldFromPermutation2Class("a");
        final Field perm2B = fieldFromPermutation2Class("b");
        final Field perm2C = fieldFromPermutation2Class("c");

        final TestCase testCase1 = new TestCase(Permutation1.class,
                                                newArrayList(newArrayList(perm1A), newArrayList(perm1B), newArrayList(perm1A, perm1B)));
        final TestCase testCase2 = new TestCase(Permutation2.class, newArrayList(newArrayList(perm2A),
                                                                                 newArrayList(perm2B),
                                                                                 newArrayList(perm2C),
                                                                                 newArrayList(perm2A, perm2B),
                                                                                 newArrayList(perm2A, perm2C),
                                                                                 newArrayList(perm2B, perm2C),
                                                                                 newArrayList(perm2A, perm2B, perm2C)));
        return Stream.of(testCase1, testCase2)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Return_All_Permutations(value)));
    }

    public Executable Should_Return_All_Permutations(final TestCase testCase) {
        return () -> {
            // given
            final List<Field> fields = TestHelper.getAllFieldsExceptDummyJacocoField(testCase.clazz);

            // when
            final List<List<Field>> permutations = FieldUtils.permutations(fields);

            // then
            assertThat(permutations).hasSameSizeAs(testCase.fields)
                                    .containsAll(testCase.fields);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Throw_Exception_When_Setter_Was_Not_Found() throws NoSuchFieldException {
        final Field fieldA = fieldFromSettersClass("a");
        final Field fieldB = fieldFromSettersClass("b");
        final Field fieldC = fieldFromSettersClass("c");
        final Field fieldD = fieldFromSettersClass("d");
        final Field fieldE = fieldFromSettersClass("e");
        final Field fieldF = fieldFromSettersClass("f");
        final Field fieldG = fieldFromSettersClass("g");

        return Stream.of(fieldA,
                         fieldB,
                         fieldC,
                         fieldD,
                         fieldE,
                         fieldF,
                         fieldG)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Throw_Exception_When_Setter_Was_Not_Found(value)));
    }

    public Executable Should_Throw_Exception_When_Setter_Was_Not_Found(final Field field) {
        return () -> {
            // when
            final Throwable result = catchThrowable(() -> FieldUtils.findSetterFor(Setters.class, field));

            // then
            assertThat(result).isInstanceOf(SetterNotFoundException.class);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Throw_Exception_When_Getter_Was_Not_Found() throws NoSuchFieldException {
        final Field fieldA = fieldFromGettersClass("a");
        final Field fieldB = fieldFromGettersClass("b");
        final Field fieldC = fieldFromGettersClass("c");
        final Field fieldD = fieldFromGettersClass("d");
        final Field fieldE = fieldFromGettersClass("e");
        final Field fieldF = fieldFromGettersClass("f");
        final Field fieldG = fieldFromGettersClass("g");

        return Stream.of(fieldA,
                         fieldB,
                         fieldC,
                         fieldD,
                         fieldE,
                         fieldF,
                         fieldG)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Throw_Exception_When_Getter_Was_Not_Found(value)));
    }

    public Executable Should_Throw_Exception_When_Getter_Was_Not_Found(final Field field) {
        return () -> {
            // when
            final Throwable result = catchThrowable(() -> FieldUtils.findGetterFor(Getters.class, field));

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

        return Stream.of(new GetterTestCase(field1, Getters.class.getMethod("isGetter1")),
                         new GetterTestCase(field2, Getters.class.getMethod("hasGetter2")),
                         new GetterTestCase(field3, Getters.class.getMethod("haveGetter3")),
                         new GetterTestCase(field4, Getters.class.getMethod("containsGetter4")),
                         new GetterTestCase(field5, Getters.class.getMethod("getGetter5")))
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Return_Expected_Getter(value)));
    }

    public Executable Should_Return_Expected_Getter(final GetterTestCase testCase) {
        return () -> {
            // when
            final Method result = FieldUtils.findGetterFor(Getters.class, testCase.field);

            // then
            assertThat(result).isEqualTo(testCase.expectedGetter);
        };
    }

    @Test
    public void Should_Return_Expected_Setter() throws NoSuchFieldException, NoSuchMethodException {
        // given
        final Field field = Setters.class.getField("goodSetter");
        final Method expectedResult = Setters.class.getMethod("setGoodSetter", field.getType());

        // when
        final Method result = FieldUtils.findSetterFor(Setters.class, field);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Field fieldFromPermutation1Class(final String name) throws java.lang.NoSuchFieldException {
        return Permutation1.class.getDeclaredField(name);
    }

    private Field fieldFromPermutation2Class(final String name) throws java.lang.NoSuchFieldException {
        return Permutation2.class.getDeclaredField(name);
    }

    private Field fieldFromSettersClass(final String name) throws java.lang.NoSuchFieldException {
        return Setters.class.getDeclaredField(name);
    }

    private Field fieldFromGettersClass(final String name) throws java.lang.NoSuchFieldException {
        return Getters.class.getDeclaredField(name);
    }

    @AllArgsConstructor
    private class TestCase {
        private Class<?> clazz;
        private List<List<Field>> fields;
    }

    @AllArgsConstructor
    private class GetterTestCase {
        private Field field;
        private Method expectedGetter;
    }
}
