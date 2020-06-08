package pl.pojo.tester.internal.utils;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import pl.pojo.tester.internal.GetOrSetValueException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class FieldUtils {

    private static final String MODIFIERS_FIELD_NAME_IN_FIELD_CLASS = "modifiers";

    private FieldUtils() {
    }

    public static List<Field> getAllFields(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(FieldUtils::isNotSynthetic)
                     .filter(FieldUtils::isNotStatic)
                     .collect(Collectors.toList());
    }

    public static List<Field> getAllFieldsExcluding(final Class<?> clazz, final List<String> excludedFields) {
        return getAllFields(clazz).stream()
                                  .filter(field -> doesNotContain(field, excludedFields))
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

    public static Object getValue(final Object targetObject, final Field field) {
        try {
            makeModifiable(field);
            return field.get(targetObject);
        } catch (final IllegalAccessException e) {
            throw new GetOrSetValueException(field.getName(), targetObject.getClass(), e);
        }
    }

    public static void setValue(final Object targetObject, final Field field, final Object value) {
        try {
            makeModifiable(field);
            field.set(targetObject, value);
        } catch (final IllegalAccessException e) {
            throw new GetOrSetValueException(field.getName(), targetObject.getClass(), e);
        }
    }

    public static List<Field> getFields(final Class<?> testedClass, final Predicate<String> predicate) {
        return getAllFields(testedClass).stream()
                                        .filter(eachField -> predicate.test(eachField.getName()))
                                        .collect(Collectors.toList());
    }

    public static boolean isFinal(final Field field) {
        final int fieldModifiers = field.getModifiers();
        return Modifier.isFinal(fieldModifiers);
    }

    public static List<Field> getSpecifiedFields(final Class<?> clazz, final List<String> names) {
        return names.stream()
                    .map(name -> getField(clazz, name))
                    .collect(Collectors.toList());
    }

    private static void makeModifiable(final Field field) {
        field.setAccessible(true);
    }

    private static boolean excludeEmptySet(final List<Field> fields) {
        return !fields.isEmpty();
    }

    private static boolean isNotSynthetic(final Field field) {
        return !field.isSynthetic();
    }

    private static boolean isNotStatic(final Field field) {
        return !Modifier.isStatic(field.getModifiers());
    }

    private static boolean doesNotContain(final Field field, final List<String> excludedFields) {
        return !excludedFields.contains(field.getName());
    }

    private static Field getField(final Class<?> clazz, final String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (final java.lang.NoSuchFieldException e) {
            throw new GetOrSetValueException(name, clazz, e);
        }
    }
}
