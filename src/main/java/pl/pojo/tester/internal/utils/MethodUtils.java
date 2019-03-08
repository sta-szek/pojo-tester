package pl.pojo.tester.internal.utils;

import org.apache.commons.lang3.reflect.TypeUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Predicate;

public final class MethodUtils {

    private MethodUtils() {
    }

    public static Method findSetterFor(final Class<?> clazz, final Field field) {
        return Arrays.stream(clazz.getMethods())
                     .filter(methodHasOnlyOneParameter())
                     .filter(areParameterAndFieldTypeAssignable(field))
                     .filter(returnTypeIsVoidOrClass())
                     .filter(method -> prefixMatchesSettersPrefixAndHasExpectedLength(method, field.getName()))
                     .findAny()
                     .orElseThrow(() -> new SetterNotFoundException(clazz, field));
    }

    public static Method findGetterFor(final Class<?> clazz, final Field field) {
        return Arrays.stream(clazz.getMethods())
                     .filter(hasZeroParameters())
                     .filter(areReturnAndFieldTypeAssignable(field))
                     .filter(method -> prefixMatchesGettersPrefixAndHasExpectedLength(method, field.getName()))
                     .findAny()
                     .orElseThrow(() -> new GetterNotFoundException(clazz, field));
    }

    private static Predicate<Method> returnTypeIsVoidOrClass() {
        return method -> Arrays.asList(method.getDeclaringClass(), void.class).contains(method.getReturnType());
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
        final String upperCaseFirstLetterFieldName = upperCaseFirstLetter(fieldName);

        if (returnType.equals(boolean.class) || returnType.equals(Boolean.class)) {
            return (methodName.startsWith("is") && methodName.equals(fieldName))
                    || (methodName.endsWith(upperCaseFirstLetterFieldName)
                       && ((methodName.startsWith("is") && (methodName.length() == (fieldNameLength + 2)))
                    || methodName.startsWith("has") && methodName.length() == fieldNameLength + 3
                    || methodName.startsWith("get") && methodName.length() == fieldNameLength + 3
                    || methodName.startsWith("have") && methodName.length() == fieldNameLength + 4
                    || methodName.startsWith("contains") && methodName.length() == fieldNameLength + 8));
        } else {
            return methodName.startsWith("get")
                   && methodName.length() == fieldNameLength + 3
                   && methodName.endsWith(upperCaseFirstLetterFieldName);
        }
    }

    private static boolean prefixMatchesSettersPrefixAndHasExpectedLength(final Method method, final String fieldName) {
        final Class<?> parameterType = method.getParameterTypes()[0];
        final String methodName = method.getName();
        final int fieldNameLength = fieldName.length();
        final String upperCaseFirstLetterFieldName = upperCaseFirstLetter(fieldName);

        if ((parameterType.equals(boolean.class) || parameterType.equals(Boolean.class))
            && fieldName.startsWith("is")) {
            final String fieldNameWithoutPrefix = fieldName.substring(2);
            return methodName.startsWith("set") && methodName.endsWith(fieldNameWithoutPrefix);

        } else {
            return methodName.startsWith("set")
                   && methodName.length() == fieldNameLength + 3
                   && methodName.endsWith(upperCaseFirstLetterFieldName);
        }
    }

    private static String upperCaseFirstLetter(final String string) {
        final String firstLetter = string.substring(0, 1)
                                         .toUpperCase();
        return firstLetter + string.substring(1, string.length());
    }

}
