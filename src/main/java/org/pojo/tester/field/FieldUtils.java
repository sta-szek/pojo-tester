package org.pojo.tester.field;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class FieldUtils {


    public static List<Field> getAllFields(final Class<?> clazz) {
        return Lists.newArrayList(clazz.getDeclaredFields());
    }

    public static List<Field> getAllFieldsExcluding(final Class<?> clazz, final List<String> excludedFields) {
        return getAllFields(clazz).stream()
                                  .filter(field -> FieldUtils.doesNotContain(field, excludedFields))
                                  .collect(Collectors.toList());
    }

    public static List<Field> getSpecifiedFields(final Class<?> clazz, final List<String> names) {
        return names.stream()
                    .map(name -> FieldUtils.getField(clazz, name))
                    .collect(Collectors.toList());
    }

    private static boolean doesNotContain(final Field field, final List<String> excludedFields) {
        return !excludedFields.contains(field.getName());
    }

    private static Field getField(final Class<?> clazz, final String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (final java.lang.NoSuchFieldException e) {
            throw new NoSuchFieldException("Could not get field " + name + " from class " + clazz.getSimpleName(), e);
        }
    }


}
