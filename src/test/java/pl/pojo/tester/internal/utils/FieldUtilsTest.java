package pl.pojo.tester.internal.utils;

import classesForTest.fields.ClassWithAllAvailableFieldModifiers;
import classesForTest.fields.Permutation1;
import classesForTest.fields.Permutation2;
import helpers.TestHelper;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


class FieldUtilsTest {

    @Test
    void Should_Return_All_Fields() {
        // given
        final List<Field> expectedFields = TestHelper.getAllFieldsExceptDummyJacocoField(
                ClassWithAllAvailableFieldModifiers.class);

        // when
        final List<Field> result = FieldUtils.getAllFields(ClassWithAllAvailableFieldModifiers.class);

        // then
        assertThat(result).containsExactlyElementsOf(expectedFields);
    }

    @Test
    void Should_Return_Specified_Fields() throws java.lang.NoSuchFieldException {
        // given
        final String fieldName = "a";
        final Field expectedField = ClassWithAllAvailableFieldModifiers.class.getDeclaredField(fieldName);

        // when
        final List<Field> result = FieldUtils.getSpecifiedFields(ClassWithAllAvailableFieldModifiers.class,
                                                                 newArrayList(fieldName));

        // then
        assertThat(result).hasSize(1)
                          .containsExactly(expectedField);
    }

    @Test
    void Should_Return_Fields_Except_Fields_With_Given_Names() {
        // given
        final String fieldName = "a";

        // when
        final List<Field> result = FieldUtils.getAllFieldsExcluding(ClassWithAllAvailableFieldModifiers.class,
                                                                    newArrayList(fieldName));

        // then
        assertThat(result).hasSize(7);
    }

    @Test
    void Should_Return_All_Fields_Names() {
        // given
        final List<String> expectedFields = TestHelper.getAllFieldsExceptDummyJacocoField(
                ClassWithAllAvailableFieldModifiers.class)
                                                      .stream()
                                                      .map(Field::getName)
                                                      .collect(Collectors.toList());

        // when
        final List<String> result = FieldUtils.getAllFieldNames(ClassWithAllAvailableFieldModifiers.class);

        // then
        assertThat(result).containsExactlyElementsOf(expectedFields);
    }

    @TestFactory
    Stream<DynamicTest> Should_Return_All_Permutations() throws NoSuchFieldException {
        final Field perm1A = fieldFromPermutation1Class("a");
        final Field perm1B = fieldFromPermutation1Class("b");
        final Field perm2A = fieldFromPermutation2Class("a");
        final Field perm2B = fieldFromPermutation2Class("b");
        final Field perm2C = fieldFromPermutation2Class("c");

        final TestCase testCase1 = new TestCase(Permutation1.class,
                                                newArrayList(newArrayList(perm1A),
                                                             newArrayList(perm1B),
                                                             newArrayList(perm1A, perm1B)));
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

    private Executable Should_Return_All_Permutations(final TestCase testCase) {
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

    @Test
    void Should_Return_True_If_Field_Is_Final() throws NoSuchFieldException {
        // given

        // when
        final boolean result = FieldUtils.isFinal(A.class.getField("a"));

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_Return_False_If_Field_Is_Not_Final() throws NoSuchFieldException {
        // given

        // when
        final boolean result = FieldUtils.isFinal(A.class.getField("b"));

        // then
        assertThat(result).isFalse();
    }

    @Test
    void Should_Return_All_Fields_Except_Static() throws NoSuchFieldException {
        // given
        final List<Field> expectedResult = new ArrayList<>();
        expectedResult.add(B.class.getDeclaredField("b"));

        // when
        final List<Field> result = FieldUtils.getAllFields(B.class);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Field fieldFromPermutation1Class(final String name) throws java.lang.NoSuchFieldException {
        return Permutation1.class.getDeclaredField(name);
    }

    private Field fieldFromPermutation2Class(final String name) throws java.lang.NoSuchFieldException {
        return Permutation2.class.getDeclaredField(name);
    }

    private static class B {
        private static int a;
        private int b;
    }

    @AllArgsConstructor
    @ToString
    private class TestCase {
        private Class<?> clazz;
        private List<List<Field>> fields;
    }

    private class A {
        public final int a = 1;
        public int b;
    }
}
