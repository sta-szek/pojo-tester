package pl.pojo.tester.api.assertion;


import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.api.FieldPredicate;
import pl.pojo.tester.api.PackageFilter;
import pl.pojo.tester.internal.instantiator.ClassLoader;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static pl.pojo.tester.internal.preconditions.ParameterPreconditions.checkNotBlank;
import static pl.pojo.tester.internal.preconditions.ParameterPreconditions.checkNotNull;

/**
 * This is the main assertions class, which should be used by clients.
 * <p>
 * Via this class assertions can be created.
 * <p>
 * For more documentation, please refer <a href="http://pojo.pl">POJO-TESTER User Guide documentation</a>
 *
 * @author Piotr Joński
 * @since 0.1.0
 */
public final class Assertions {

    private Assertions() {}

    /**
     * Creates assertion for class, by qualified class name.
     *
     * @param qualifiedClassName class for assertion
     *
     * @return assertion for given class
     *
     * @see AbstractAssertion
     * @see MultiClassAssertion
     * @see SingleClassAssertion
     */
    public static AbstractAssertion assertPojoMethodsFor(final String qualifiedClassName) {
        checkNotBlank("qualifiedClassName", qualifiedClassName);

        final Class<?> clazz = ClassLoader.loadClass(qualifiedClassName);
        return assertPojoMethodsFor(clazz);
    }

    /**
     * Creates assertion for class.
     *
     * @param clazz class for assertion
     *
     * @return assertion for given class
     *
     * @see AbstractAssertion
     * @see MultiClassAssertion
     * @see SingleClassAssertion
     */
    public static AbstractAssertion assertPojoMethodsFor(final Class<?> clazz) {
        checkNotNull("clazz", clazz);

        final Predicate<String> predicateAcceptingAllFields = FieldPredicate.includeAllFields(clazz);
        return assertPojoMethodsFor(clazz, predicateAcceptingAllFields);
    }

    /**
     * Creates assertion for class, by qualified class name and field predicate.
     *
     * @param qualifiedClassName class for assertion
     * @param fieldPredicate     field predicate for given class
     *
     * @return assertion for given class
     *
     * @see AbstractAssertion
     * @see MultiClassAssertion
     * @see SingleClassAssertion
     */
    public static AbstractAssertion assertPojoMethodsFor(final String qualifiedClassName, final Predicate<String> fieldPredicate) {
        checkNotBlank("qualifiedClassName", qualifiedClassName);
        checkNotNull("fieldPredicate", fieldPredicate);

        final Class<?> clazz = ClassLoader.loadClass(qualifiedClassName);
        return assertPojoMethodsFor(clazz, fieldPredicate);
    }

    /**
     * Creates assertion for class and field predicate.
     *
     * @param clazz          class for assertion
     * @param fieldPredicate field predicate for given class
     *
     * @return assertion for given class
     *
     * @see AbstractAssertion
     * @see MultiClassAssertion
     * @see SingleClassAssertion
     */
    public static AbstractAssertion assertPojoMethodsFor(final Class<?> clazz, final Predicate<String> fieldPredicate) {
        checkNotNull("clazz", clazz);
        checkNotNull("fieldPredicate", fieldPredicate);

        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(clazz,
                                                                                                     fieldPredicate);
        return assertPojoMethodsFor(classAndFieldPredicatePair);
    }

    /**
     * Creates assertion for classes declared as {@link ClassAndFieldPredicatePair} objects.
     *
     * @param baseClassAndFieldPredicatePair base class to test
     * @param classAndFieldPredicatePairs    nested classes, which are used as field types in base class
     *
     * @return assertion for given base class
     *
     * @see AbstractAssertion
     * @see MultiClassAssertion
     * @see SingleClassAssertion
     */
    public static AbstractAssertion assertPojoMethodsFor(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                                                         final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        checkNotNull("baseClassAndFieldPredicatePair", baseClassAndFieldPredicatePair);
        return new SingleClassAssertion(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs);
    }

    /**
     * Creates assertion for all classes, by classes names.
     *
     * @param qualifiedClassNames classes to test
     *
     * @return assertion for all classes
     *
     * @see AbstractAssertion
     * @see MultiClassAssertion
     * @see SingleClassAssertion
     */
    public static AbstractAssertion assertPojoMethodsForAll(final String... qualifiedClassNames) {
        checkNotBlank("qualifiedClassNames", qualifiedClassNames);

        final Class<?>[] classesAndFieldPredicatesPairs = Arrays.stream(qualifiedClassNames)
                                                                .map(ClassLoader::loadClass)
                                                                .toArray(Class[]::new);
        return assertPojoMethodsForAll(classesAndFieldPredicatesPairs);
    }

    /**
     * Creates assertion for all classes returned by {@link PackageFilter}.
     *
     * @param packageFilter package filter
     *
     * @return assertion for all classes
     *
     * @see PackageFilter
     */
    public static AbstractAssertion assertPojoMethodsForAll(final PackageFilter packageFilter) {
        checkNotNull("packageFilter", packageFilter);
        return assertPojoMethodsForAll(packageFilter.getClasses());
    }

    /**
     * Creates assertion for all classes.
     *
     * @param classes classes to test
     *
     * @return assertion for all classes
     *
     * @see AbstractAssertion
     * @see MultiClassAssertion
     * @see SingleClassAssertion
     */
    public static AbstractAssertion assertPojoMethodsForAll(final Class... classes) {
        checkNotNull("classes", classes);

        final ClassAndFieldPredicatePair[] classesAndFieldPredicatesPairs = Arrays.stream(classes)
                                                                                  .map(ClassAndFieldPredicatePair::new)
                                                                                  .toArray(ClassAndFieldPredicatePair[]::new);
        return assertPojoMethodsForAll(classesAndFieldPredicatesPairs);
    }

    /**
     * Creates assertion for all classes declared as {@link ClassAndFieldPredicatePair} objects.
     *
     * @param classesAndFieldPredicatesPairs class and field predicate pairs to test
     *
     * @return assertion for all classes
     *
     * @see AbstractAssertion
     * @see MultiClassAssertion
     * @see SingleClassAssertion
     */
    public static AbstractAssertion assertPojoMethodsForAll(final ClassAndFieldPredicatePair... classesAndFieldPredicatesPairs) {
        checkNotNull("classesAndFieldPredicatesPairs", classesAndFieldPredicatesPairs);

        final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs = Arrays.asList(
                classesAndFieldPredicatesPairs);
        return new MultiClassAssertion(classAndFieldPredicatePairs);
    }

}
