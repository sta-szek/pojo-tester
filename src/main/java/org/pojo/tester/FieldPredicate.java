package org.pojo.tester;

import org.pojo.tester.field.FieldUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public final class FieldPredicate {

    private FieldPredicate() {
    }

    public static Predicate<String> includeAllFields(final Class<?> testedClass) {
        final List<String> allFieldNames = FieldUtils.getAllFieldNames(testedClass);
        Predicate<String> predicate = getAlwaysFalsePredicate();
        for (final String filedName : allFieldNames) {
            predicate = predicate.or(getEqualPredicate(filedName));
        }
        return predicate;
    }

    public static Predicate<String> include(final List<String> includedFields) {
        Predicate<String> predicate = getAlwaysFalsePredicate();
        for (final String filedName : includedFields) {
            predicate = predicate.or(getEqualPredicate(filedName));
        }
        return predicate;
    }

    public static Predicate<String> include(final String... includedFields) {
        return include(Arrays.asList(includedFields));
    }

    public static Predicate<String> exclude(final List<String> excludedFields) {
        Predicate<String> predicate = getAlwaysTruePredicate();
        for (final String filedName : excludedFields) {
            predicate = predicate.and(getNonEqualPredicate(filedName));
        }
        return predicate;
    }

    public static Predicate<String> exclude(final String... excludedFields) {
        return exclude(Arrays.asList(excludedFields));
    }

    private static Predicate<String> getAlwaysFalsePredicate() {
        return fieldName -> false;
    }

    private static Predicate<String> getAlwaysTruePredicate() {
        return fieldName -> true;
    }

    private static Predicate<String> getNonEqualPredicate(final String filedName) {
        return getEqualPredicate(filedName).negate();
    }

    private static Predicate<String> getEqualPredicate(final String filedName) {
        return otherFieldName -> otherFieldName.equals(filedName);
    }

}
