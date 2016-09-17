package pl.pojo.tester.internal.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.lang3.reflect.TypeUtils;
import pl.pojo.tester.api.GetterNotFoundException;
import pl.pojo.tester.api.SetterNotFoundException;

public final class MethodUtils {

    private MethodUtils() {
    }

    public static Method findSetterFor(final Class<?> clazz, final Field field) {
        final Method[] methods = clazz.getMethods();
        final String fieldName = upperCaseFirstLetter(field.getName());
        return Stream.of(methods)
                     .filter(method -> prefixMatchesSettersPrefixAndHasExpectedLength(method, fieldName))
                     .filter(methodNameEndsWithFieldName(fieldName))
                     .filter(methodHasOnlyOneParameter())
                     .filter(areParameterAndFieldTypeAssignable(field))
                     .filter(returnTypeIsVoid())
                     .findAny()
                     .orElseThrow(() -> new SetterNotFoundException(clazz, field));
    }

    public static Method findGetterFor(final Class<?> clazz, final Field field) {
        final Method[] methods = clazz.getMethods();
        final String fieldName = upperCaseFirstLetter(field.getName());
        return Stream.of(methods)
                     .filter(method -> prefixMatchesGettersPrefixAndHasExpectedLength(method, fieldName))
                     .filter(methodNameEndsWithFieldName(fieldName))
                     .filter(hasZeroParameters())
                     .filter(areReturnAndFieldTypeAssignable(field))
                     .findAny()
                     .orElseThrow(() -> new GetterNotFoundException(clazz, field));
    }

    private static Predicate<Method> returnTypeIsVoid() {
        return method -> method.getReturnType() == void.class;
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

    private static Predicate<Method> methodNameEndsWithFieldName(final String fieldName) {
        return method -> method.getName()
                               .endsWith(fieldName);
    }

    private static boolean prefixMatchesGettersPrefixAndHasExpectedLength(final Method method, final String fieldName) {
        final Class<?> returnType = method.getReturnType();
        final String methodName = method.getName();
        final int fieldNameLength = fieldName.length();
        if (returnType.equals(boolean.class) || returnType.equals(Boolean.class)) {
            return (methodName.startsWith("is") && methodName.length() == fieldNameLength + 2)
                   || (methodName.startsWith("has") && methodName.length() == fieldNameLength + 3)
                   || (methodName.startsWith("get") && methodName.length() == fieldNameLength + 3)
                   || (methodName.startsWith("have") && methodName.length() == fieldNameLength + 4)
                   || (methodName.startsWith("contains") && methodName.length() == fieldNameLength + 8);
        } else {
            return methodName.startsWith("get") && methodName.length() == fieldNameLength + 3;
        }
    }

    private static boolean prefixMatchesSettersPrefixAndHasExpectedLength(final Method method, final String fieldName) {
        final String methodName = method.getName();
        final int fieldNameLength = fieldName.length();
        return methodName.startsWith("set") && methodName.length() == fieldNameLength + 3;
    }

    private static String upperCaseFirstLetter(final String string) {
        final String firstLetter = string.substring(0, 1)
                                         .toUpperCase();
        return firstLetter + string.substring(1, string.length());
    }

}
