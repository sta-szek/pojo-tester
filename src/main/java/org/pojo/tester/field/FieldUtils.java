package org.pojo.tester.field;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class FieldUtils {

    private FieldUtils() {}

    public static List<Field> getAllFields(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(FieldUtils::isNotSynthetic)
                     .collect(Collectors.toList());
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

    public static List<List<Field>> permutations(final List<Field> fields) {
        final ICombinatoricsVector<Field> vector = Factory.createVector(fields);
        final Generator<Field> subSetGenerator = Factory.createSubSetGenerator(vector);
        return subSetGenerator.generateAllObjects()
                              .stream()
                              .map(ICombinatoricsVector::getVector)
                              .filter(FieldUtils::excludeEmptySet)
                              .collect(Collectors.toList());
    }

    private static boolean excludeEmptySet(final List<Field> fields) {
        return !fields.isEmpty();
    }

    private static boolean isNotSynthetic(final Field field) {
        return !field.isSynthetic();
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
