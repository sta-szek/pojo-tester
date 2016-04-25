package org.pojo.tester.field;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.fields.ClassWithAllAvailableFieldModifiers;
import test.fields.Permutation1;
import test.fields.Permutation2;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

@RunWith(JUnitParamsRunner.class)
public class FieldUtilsTest {

    @Test
    public void shouldReturnAllFields() {
        // given
        final List<Field> expectedFields = getAllFieldsExceptDummyJacocoField(ClassWithAllAvailableFieldModifiers.class);

        // when
        final List<Field> result = FieldUtils.getAllFields(ClassWithAllAvailableFieldModifiers.class);

        // then
        assertThat(result).hasSize(16)
                          .containsExactlyElementsOf(expectedFields);
    }

    @Test
    public void shouldReturnSpecifiedFields() throws java.lang.NoSuchFieldException {
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
    public void shouldReturnFields_ExceptFieldsWithGivenNames() {
        // given
        final String fieldName = "a";

        // when
        final List<Field> result = FieldUtils.getAllFieldsExcluding(ClassWithAllAvailableFieldModifiers.class, newArrayList(fieldName));

        // then
        assertThat(result).hasSize(15);
    }

    @Test
    public void shouldReturnAllFieldsNames() {
        // given
        final List<String> expectedFields = getAllFieldsExceptDummyJacocoField(ClassWithAllAvailableFieldModifiers.class).stream()
                                                                                                                         .map(Field::getName)
                                                                                                                         .collect(Collectors.toList());

        // when
        final List<String> result = FieldUtils.getAllFieldNames(ClassWithAllAvailableFieldModifiers.class);

        // then
        assertThat(result).hasSize(16)
                          .containsExactlyElementsOf(expectedFields);
    }

    @Test
    @Parameters(method = "permutationFields")
    public void shouldReturnAllPermutations(final Class<?> clazz, final List<List<Field>> expectedPermutations) {
        // given
        final List<Field> fields = getAllFieldsExceptDummyJacocoField(clazz);

        // when
        final List<List<Field>> permutations = FieldUtils.permutations(fields);

        // then
        assertThat(permutations).hasSameSizeAs(expectedPermutations)
                                .containsAll(expectedPermutations);
    }

    private Object[][] permutationFields() throws java.lang.NoSuchFieldException {
        final Field perm1A = fieldFromPermutation1Class("a");
        final Field perm1B = fieldFromPermutation1Class("b");
        final Field perm2A = fieldFromPermutation2Class("a");
        final Field perm2B = fieldFromPermutation2Class("b");
        final Field perm2C = fieldFromPermutation2Class("c");
        return new Object[][]{
                {Permutation1.class, newArrayList(newArrayList(perm1A), newArrayList(perm1B), newArrayList(perm1A, perm1B))},
                {Permutation2.class, newArrayList(newArrayList(perm2A),
                                                  newArrayList(perm2B),
                                                  newArrayList(perm2C),
                                                  newArrayList(perm2A, perm2B),
                                                  newArrayList(perm2A, perm2C),
                                                  newArrayList(perm2B, perm2C),
                                                  newArrayList(perm2A, perm2B, perm2C))}
        };
    }

    private Field fieldFromPermutation1Class(final String name) throws java.lang.NoSuchFieldException {
        return Permutation1.class.getDeclaredField(name);
    }

    private Field fieldFromPermutation2Class(final String name) throws java.lang.NoSuchFieldException {
        return Permutation2.class.getDeclaredField(name);
    }

    private List<Field> getAllFieldsExceptDummyJacocoField(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(field -> !field.getName()
                                            .equals("$jacocoData"))
                     .collect(Collectors.toList());
    }

}