package pl.pojo.tester.internal.utils;

import org.apache.commons.lang3.reflect.TypeUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class MethodUtils {

    private MethodUtils() {
    }

    public static Method findSetterFor(final Class<?> clazz, final Field field) {
        return findSetterFor(clazz, field, false);
    }

    public static Method findSetterFor(final Class<?> clazz, final Field field, final boolean fluent) {
        final List<Method> candidates = Arrays.stream(clazz.getMethods())
                                             .filter(methodHasOnlyOneParameter())
                                             .filter(areParameterAndFieldTypeAssignable(field))
                                             .filter(returnsVoidOrTheTestedClass(clazz))
                                             .collect(Collectors.toList());
        return candidates.stream()
                         .filter(method -> prefixMatchesSettersPrefixAndHasExpectedLength(method, field.getName()))
                         .findAny()
                         .orElseGet(() -> fluentSetter(clazz, field, candidates, fluent));
    }

    public static Method findGetterFor(final Class<?> clazz, final Field field) {
        return findGetterFor(clazz, field, false);
    }

    public static Method findGetterFor(final Class<?> clazz, final Field field, final boolean fluent) {
        final List<Method> candidates = Arrays.stream(clazz.getMethods())
                                             .filter(hasZeroParameters())
                                             .filter(areReturnAndFieldTypeAssignable(field))
                                             .collect(Collectors.toList());
        return candidates.stream()
                         .filter(method -> prefixMatchesGettersPrefixAndHasExpectedLength(method, field.getName()))
                         .findAny()
                         .orElseGet(() -> fluentGetter(clazz, field, candidates, fluent));
    }

    private static Method fluentGetter(final Class<?> clazz,
                                       final Field field,
                                       final List<Method> candidates,
                                       final boolean fluent) {
        if (!fluent) {
            throw new GetterNotFoundException(clazz, field);
        }
        return candidates.stream()
                         .filter(method -> method.getName().equals(field.getName()))
                         .findAny()
                         .orElseThrow(() -> new GetterNotFoundException(clazz, field));
    }

    private static Method fluentSetter(final Class<?> clazz,
                                       final Field field,
                                       final List<Method> candidates,
                                       final boolean fluent) {
        if (!fluent) {
            throw new SetterNotFoundException(clazz, field);
        }
        return candidates.stream()
                         .filter(method -> method.getName().equals(field.getName()))
                         .findAny()
                         .orElseThrow(() -> new SetterNotFoundException(clazz, field));
    }

    private static Predicate<Method> returnsVoidOrTheTestedClass(final Class<?> clazz) {
        return method -> {
            final Class<?> returnType = method.getReturnType();
            return returnType == void.class || (returnType != Object.class && returnType.isAssignableFrom(clazz));
        };
    }

    private static Predicate<Method> areParameterAndFieldTypeAssignable(final Field field) {
        return method -> TypeUtils.isAssignable(method.getParameterTypes()[0], field.getType());
    }

    private static Predicate<Method> methodHasOnlyOneParameter() {
        return method -> method.getParameterCount() == 1;
    }

    private static Predicate<Method> areReturnAndFieldTypeAssignable(final Field field) {
        return method -> TypeUtils.isAssignable(method.getReturnType(), field.getType());
    }

    private static Predicate<Method> hasZeroParameters() {
        return method -> method.getParameterCount() == 0;
    }

    private static boolean prefixMatchesGettersPrefixAndHasExpectedLength(final Method method, final String fieldName) {
        final Class<?> returnType = method.getReturnType();
        final String methodName = method.getName();
        final int fieldNameLength = fieldName.length();
        final String propertySuffix = beanPropertySuffix(fieldName);

        if (returnType.equals(boolean.class) || returnType.equals(Boolean.class)) {
            return (methodName.startsWith("is") && methodName.equals(fieldName))
                    || (methodName.endsWith(propertySuffix)
                       && ((methodName.startsWith("is") && (methodName.length() == (fieldNameLength + 2)))
                    || methodName.startsWith("has") && methodName.length() == fieldNameLength + 3
                    || methodName.startsWith("get") && methodName.length() == fieldNameLength + 3
                    || methodName.startsWith("have") && methodName.length() == fieldNameLength + 4
                    || methodName.startsWith("contains") && methodName.length() == fieldNameLength + 8));
        } else {
            return methodName.startsWith("get")
                   && methodName.length() == fieldNameLength + 3
                   && methodName.endsWith(propertySuffix);
        }
    }

    private static boolean prefixMatchesSettersPrefixAndHasExpectedLength(final Method method, final String fieldName) {
        final Class<?> parameterType = method.getParameterTypes()[0];
        final String methodName = method.getName();
        final int fieldNameLength = fieldName.length();
        final String propertySuffix = beanPropertySuffix(fieldName);

        if ((parameterType.equals(boolean.class) || parameterType.equals(Boolean.class))
            && fieldName.startsWith("is")) {
            final String fieldNameWithoutPrefix = fieldName.substring(2);
            return methodName.startsWith("set") && methodName.endsWith(fieldNameWithoutPrefix);

        } else {
            return methodName.startsWith("set")
                   && methodName.length() == fieldNameLength + 3
                   && methodName.endsWith(propertySuffix);
        }
    }

    private static String beanPropertySuffix(final String fieldName) {
        if (fieldName.length() > 1 && Character.isUpperCase(fieldName.charAt(1))) {
            return fieldName;
        }
        return upperCaseFirstLetter(fieldName);
    }

    private static String upperCaseFirstLetter(final String string) {
        final String firstLetter = string.substring(0, 1)
                                         .toUpperCase();
        return firstLetter + string.substring(1, string.length());
    }

}
