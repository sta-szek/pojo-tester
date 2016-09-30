package pl.pojo.tester.api;

import java.util.function.Predicate;
import lombok.Getter;
import pl.pojo.tester.internal.instantiator.ClassLoader;

/**
 * This class is an encapsulation for {@code class} that will be tested and fields to test.
 *
 * @author Piotr Joński
 * @since 0.1.0
 */
@Getter
public class ClassAndFieldPredicatePair {
    private final Class<?> clazz;
    private final Predicate<String> fieldsPredicate;

    /**
     * Instantiates {@code ClassAndFieldPredicatePair} with given class and fields predicate.
     *
     * @param clazz           class to test
     * @param fieldsPredicate field of {@code clazz} to test
     */
    public ClassAndFieldPredicatePair(final Class<?> clazz, final Predicate<String> fieldsPredicate) {
        this.clazz = clazz;
        this.fieldsPredicate = fieldsPredicate;
    }

    /**
     * Instantiates {@code ClassAndFieldPredicatePair} with given class and default fields predicate.
     * Default field predicate accepts all fields of given class.
     *
     * @param clazz class to test
     */
    public ClassAndFieldPredicatePair(final Class<?> clazz) {
        this(clazz, FieldPredicate.includeAllFields(clazz));
    }

    /**
     * Instantiates {@code ClassAndFieldPredicatePair} with given qualified class name and default fields predicate.
     * Default field predicate accepts all fields of given class.
     *
     * @param qualifiedClassName qualified class name to test
     * @param fieldsPredicate    field of {@code clazz} to test
     */
    public ClassAndFieldPredicatePair(final String qualifiedClassName, final Predicate<String> fieldsPredicate) {
        this(ClassLoader.loadClass(qualifiedClassName), fieldsPredicate);
    }

    /**
     * Instantiates {@code ClassAndFieldPredicatePair} with given qualified class name and default fields predicate.
     * Default field predicate accepts all fields of given class.
     *
     * @param qualifiedClassName qualified class name to test
     */
    public ClassAndFieldPredicatePair(final String qualifiedClassName) {
        this(ClassLoader.loadClass(qualifiedClassName));
    }
}
