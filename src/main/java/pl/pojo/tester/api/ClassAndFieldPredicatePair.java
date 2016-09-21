package pl.pojo.tester.api;

import java.util.function.Predicate;
import lombok.Getter;
import pl.pojo.tester.internal.instantiator.ClassLoader;

@Getter
public class ClassAndFieldPredicatePair {
    private final Class<?> clazz;
    private final Predicate<String> fieldsPredicate;

    public ClassAndFieldPredicatePair(final Class<?> clazz, final Predicate<String> fieldsPredicate) {
        this.clazz = clazz;
        this.fieldsPredicate = fieldsPredicate;
    }

    public ClassAndFieldPredicatePair(final Class<?> clazz) {
        this(clazz, FieldPredicate.includeAllFields(clazz));
    }

    public ClassAndFieldPredicatePair(final String qualifiedClassName, final Predicate<String> fieldsPredicate) {
        this(ClassLoader.loadClass(qualifiedClassName), fieldsPredicate);
    }

    public ClassAndFieldPredicatePair(final String qualifiedClassName) {
        this(ClassLoader.loadClass(qualifiedClassName));
    }
}
