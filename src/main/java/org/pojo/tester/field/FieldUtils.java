package org.pojo.tester.field;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import org.pojo.tester.GetOrSetValueException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class FieldUtils {

    public static final String FIELD_CLASS_MODIFIER_FIELD_NAME = "modifiers";

    private FieldUtils() {}

    public static List<Field> getAllFields(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(FieldUtils::isNotSynthetic)
                     .collect(Collectors.toList());
    }

    public static List<Field> getAllFieldsExcluding(final Class<?> clazz, final List<String> excludedFields) {
        return getAllFields(clazz).stream()
                                  .filter(field -> doesNotContain(field, excludedFields))
                                  .collect(Collectors.toList());
    }

    public static List<Field> getSpecifiedFields(final Class<?> clazz, final List<String> names) {
        return names.stream()
                    .map(name -> getField(clazz, name))
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

    public static List<String> getAllFieldNames(final Class<?> clazz) {
        return getAllFields(clazz).stream()
                                  .map(Field::getName)
                                  .collect(Collectors.toList());
    }

    public static void makeModifiable(final Field field) {
        final Class<? extends Field> clazz = field.getClass();
        try {
            field.setAccessible(true);
            final Field modifierField = clazz.getDeclaredField(FIELD_CLASS_MODIFIER_FIELD_NAME);
            modifierField.setAccessible(true);

            int modifiers = field.getModifiers();
            modifiers = modifiers & ~Modifier.FINAL;
            modifierField.setInt(field, modifiers);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new GetOrSetValueException(FIELD_CLASS_MODIFIER_FIELD_NAME, clazz, e);
        }
    }

    public static Object getValue(final Object targetObject, final Field field) throws IllegalAccessException {
        makeModifiable(field);
        return field.get(targetObject);
    }

    public static void setValue(final Object targetObject, final Field field, final Object value) throws IllegalAccessException {
        makeModifiable(field);
        field.set(targetObject, value);
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
            throw new GetValueException(name, clazz, e);
        }
    }
}
