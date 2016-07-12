package org.pojo.tester.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.pojo.tester.GetterNotFoundException;
import org.pojo.tester.SetterNotFoundException;

public class MethodUtils {

    public static Method findSetterFor(final Class<?> clazz, final Field field) {
        final Method[] methods = clazz.getMethods();
        final String fieldName = upperCaseFirstLetter(field.getName());
        return Stream.of(methods)
                     .filter(method -> method.getParameterCount() == 1)
                     .filter(method -> TypeUtils.isAssignable(method.getParameterTypes()[0], field.getType()))
                     .filter(method -> method.getReturnType() == void.class)
                     .filter(method -> method.getName()
                                             .endsWith(fieldName))
                     .filter(method -> method.getName()
                                             .startsWith("set"))
                     .filter(method -> method.getName()
                                             .length() == fieldName.length() + 3)
                     .findAny()
                     .orElseThrow(() -> new SetterNotFoundException(clazz, field));
    }

    public static Method findGetterFor(final Class<?> clazz, final Field field) {
        final Method[] methods = clazz.getMethods();
        final String fieldName = upperCaseFirstLetter(field.getName());
        return Stream.of(methods)
                     .filter(method -> prefixMatchesGettersPrefixAndHasExpectedLength(method, fieldName))
                     .filter(method -> method.getParameterCount() == 0)
                     .filter(method -> TypeUtils.isAssignable(method.getReturnType(), field.getType()))
                     .filter(method -> method.getName()
                                             .endsWith(fieldName))
                     .findAny()
                     .orElseThrow(() -> new GetterNotFoundException(clazz, field));
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
            return methodName.startsWith("get");
        }
    }

    private static String upperCaseFirstLetter(final String string) {
        final String firstLetter = string.substring(0, 1)
                                         .toUpperCase();
        return firstLetter + string.substring(1, string.length());
    }

}
