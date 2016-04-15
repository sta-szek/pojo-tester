package org.pojo.tester.field;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.fields.ClassWithAllAvailableFieldModifiers;
import test.fields.Permutation1;
import test.fields.Permutation2;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
    @Parameters(method = "permutationFields")
    public void shouldReturnAllPermutations(final Class<?> clazz, final List<List<Field>> expectedPermutations) {
        // given
        final ArrayList<Field> fields = newArrayList(clazz.getDeclaredFields());

        // when
        final List<List<Field>> permutations = FieldUtils.permutations(fields);

        // then
        assertThat(permutations).hasSameSizeAs(expectedPermutations)
                                .containsAll(expectedPermutations);
    }

    private List<Field> getAllFieldsExceptDummyJacocoField(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(field -> field.getName() != "$jacocoData")
                     .collect(Collectors.toList());
    }

    private Object[][] permutationFields() throws java.lang.NoSuchFieldException {
        return new Object[][]{
                {Permutation1.class, newArrayList(newArrayList(fieldFromPermutation1Class("a")),
                                                  newArrayList(fieldFromPermutation1Class("b")),
                                                  newArrayList(fieldFromPermutation1Class("a"), fieldFromPermutation1Class("b")))},
                {Permutation2.class, newArrayList(newArrayList(fieldFromPermutation2Class("a")),
                                                  newArrayList(fieldFromPermutation2Class("b")),
                                                  newArrayList(fieldFromPermutation2Class("c")),
                                                  newArrayList(fieldFromPermutation2Class("a"), fieldFromPermutation2Class("b")),
                                                  newArrayList(fieldFromPermutation2Class("a"), fieldFromPermutation2Class("c")),
                                                  newArrayList(fieldFromPermutation2Class("b"), fieldFromPermutation2Class("c")),
                                                  newArrayList(fieldFromPermutation2Class("a"),
                                                               fieldFromPermutation2Class("b"),
                                                               fieldFromPermutation2Class("c")))}
        };
    }

    private Field fieldFromPermutation1Class(final String name) throws java.lang.NoSuchFieldException {
        return Permutation1.class.getDeclaredField(name);
    }

    private Field fieldFromPermutation2Class(final String name) throws java.lang.NoSuchFieldException {
        return Permutation2.class.getDeclaredField(name);
    }

}