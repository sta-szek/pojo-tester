package org.pojo.tester;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClassAndFieldPredicatePair {
    private final Class testedClass;
    private final Predicate<String> predicate;

    ClassAndFieldPredicatePair(final Class testedClass) {
        this(testedClass, FieldPredicate.includeAllFields(testedClass));
    }
}
