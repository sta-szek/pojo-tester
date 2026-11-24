package pl.pojo.tester.internal.utils;

import org.paukov.combinatorics.CombinatoricsFactory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import pl.pojo.tester.internal.GetOrSetValueException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
        final ICombinatoricsVector<Field> vector = CombinatoricsFactory.createVector(fields);
        final Generator<Field> subSetGenerator = CombinatoricsFactory.createSubSetGenerator(vector);
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
            field.setAccessible(true);
            return field.get(targetObject);
        } catch (final IllegalAccessException e) {
            throw new GetOrSetValueException(field.getName(), targetObject.getClass(), e);
        }
    }

    public static void setValue(final Object targetObject, final Field field, final Object value) {
        try {
            makeModifiable(field);
            field.set(targetObject, value);
        } catch (final IllegalAccessException reflectiveSetFailed) {
            try {
                // Java 12+ removed Field.modifiers. VarHandle is called reflectively so this class
                // still compiles as Java 8 bytecode.
                setViaVarHandle(targetObject, field, value);
            } catch (final ReflectiveOperationException varHandleFailed) {
                throw new GetOrSetValueException(field.getName(), targetObject.getClass(), reflectiveSetFailed);
            }
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
        if (!isFinal(field)) {
            // A non-final field does not need Field.modifiers. Reading it warns on JDK 9 through 11.
            return;
        }
        try {
            final Field modifierField = Field.class.getDeclaredField(MODIFIERS_FIELD_NAME_IN_FIELD_CLASS);
            modifierField.setAccessible(true);
            final int modifiers = field.getModifiers() & ~Modifier.FINAL;
            modifierField.setInt(field, modifiers);
        } catch (final Exception ignored) {
            // Field.modifiers is gone on Java 12+. setValue falls back to VarHandle.
        }
    }

    private static void setViaVarHandle(final Object target, final Field field, final Object value)
            throws ReflectiveOperationException {
        final Class<?> methodHandles = Class.forName("java.lang.invoke.MethodHandles");
        final Class<?> lookupType = Class.forName("java.lang.invoke.MethodHandles$Lookup");
        final Object lookup = methodHandles.getMethod("lookup").invoke(null);
        final Object privateLookup = methodHandles.getMethod("privateLookupIn", Class.class, lookupType)
                                                  .invoke(null, field.getDeclaringClass(), lookup);
        final Object varHandle = lookupType.getMethod("findVarHandle", Class.class, String.class, Class.class)
                                           .invoke(privateLookup,
                                                   field.getDeclaringClass(),
                                                   field.getName(),
                                                   field.getType());
        final Method set = Class.forName("java.lang.invoke.VarHandle").getMethod("set", Object[].class);
        set.invoke(varHandle, new Object[]{new Object[]{target, value}});
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
