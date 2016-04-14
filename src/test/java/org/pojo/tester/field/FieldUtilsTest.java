package org.pojo.tester.field;

import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.fields.ClassWithAllAvailableFieldModifiers;

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

    private List<Field> getAllFieldsExceptDummyJacocoField(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(field -> field.getName() != "$jacocoData")
                     .collect(Collectors.toList());
    }

}