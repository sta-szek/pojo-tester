package org.pojo.tester.field;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import org.pojo.tester.GetOrSetValueException;
import org.pojo.tester.GetterNotFoundException;
import org.pojo.tester.SetterNotFoundException;

public final class FieldUtils {

    public static final String FIELD_CLASS_MODIFIER_FIELD_NAME = "modifiers";

    private FieldUtils() {
    }

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

    public static Object getValue(final Object targetObject, final Field field) throws IllegalAccessException {
        makeModifiable(field);
        return field.get(targetObject);
    }

    public static void setValue(final Object targetObject, final Field field, final Object value) throws IllegalAccessException {
        makeModifiable(field);
        field.set(targetObject, value);
    }

    public static List<Field> getFields(final Class<?> testedClass, final Predicate<String> predicate) {
        return getAllFields(testedClass).stream()
                                        .filter(eachField -> predicate.test(eachField.getName()))
                                        .collect(Collectors.toList());
    }

    public static Method findSetterFor(final Class<?> clazz, final Field field) {
        final Method[] methods = clazz.getMethods();
        final String fieldName = upperCaseFirstLetter(field.getName());
        return Stream.of(methods)
                     .filter(method -> method.getParameterCount() == 1)
                     .filter(method -> method.getParameterTypes()[0] == field.getType())
                     .filter(method -> method.getReturnType() == void.class)
                     .map(Method::getName)
                     .filter(methodName -> methodName.endsWith(fieldName))
                     .filter(methodName -> methodName.startsWith("set"))
                     .filter(methodName -> methodName.length() == fieldName.length() + 3)
                     .map(methodName -> getSetterByNameAndParametersForField(clazz, methodName, field))
                     .findAny()
                     .orElseThrow(() -> new SetterNotFoundException(clazz, field));
    }

    public static Method findGetterFor(final Class<?> clazz, final Field field) {
        final Method[] methods = clazz.getMethods();
        final String fieldName = upperCaseFirstLetter(field.getName());
        return Stream.of(methods)
                     .filter(method -> prefixMatchesGettersPrefixAndHasExpectedLength(method, fieldName))
                     .filter(method -> method.getParameterCount() == 0)
                     .filter(method -> method.getReturnType() == field.getType())
                     .map(Method::getName)
                     .filter(methodName -> methodName.endsWith(fieldName))
                     .map(methodName -> getGetterByNameForField(clazz, methodName, field))
                     .findAny()
                     .orElseThrow(() -> new GetterNotFoundException(clazz, field));
    }


    private static Method getGetterByNameForField(final Class<?> clazz, final String methodName, final Field field) {
        try {
            return clazz.getMethod(methodName);
        } catch (final NoSuchMethodException e) {
            throw new GetterNotFoundException(clazz, field);
        }
    }

    private static Method getSetterByNameAndParametersForField(final Class<?> clazz, final String methodName, final Field field) {
        try {
            return clazz.getMethod(methodName, field.getType());
        } catch (final NoSuchMethodException e) {
            throw new SetterNotFoundException(clazz, field);
        }
    }

    private static void makeModifiable(final Field field) {
        final Class<? extends Field> clazz = field.getClass();
        try {
            field.setAccessible(true);
            final Field modifierField = clazz.getDeclaredField(FIELD_CLASS_MODIFIER_FIELD_NAME);
            modifierField.setAccessible(true);

            final int modifiers = field.getModifiers() & ~Modifier.FINAL;
            modifierField.setInt(field, modifiers);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new GetOrSetValueException(FIELD_CLASS_MODIFIER_FIELD_NAME, clazz, e);
        }
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

    private static boolean prefixMatchesGettersPrefixAndHasExpectedLength(final Method method, final String fieldName) {
        final Class<?> returnType = method.getReturnType();
        final String methodName = method.getName();
        final int fieldNameLength = fieldName.length();
        if (returnType.equals(boolean.class) || returnType.equals(Boolean.class)) {
            return (methodName.startsWith("is") && methodName.length() == fieldNameLength + 2)
                   || (methodName.startsWith("has") && methodName.length() == fieldNameLength + 3)
                   || (methodName.startsWith("have") && methodName.length() == fieldNameLength + 4)
                   || (methodName.startsWith("contains") && methodName.length() == fieldNameLength + 8);
        } else {
            return methodName.startsWith("get");
        }
    }

    private static String upperCaseFirstLetter(final String string) {
        final String firstLetter = string.substring(0, 1)
                                         .toUpperCase();
        return firstLetter + string.substring(1, string.length());
    }
}
