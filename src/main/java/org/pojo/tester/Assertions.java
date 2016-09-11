package org.pojo.tester;


import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Assertions {

    public static AbstractAssetion assertPojoMethodsFor(final Class<?> clazz) {
        final Predicate<String> predicateAcceptingAllFields = FieldPredicate.includeAllFields(clazz);
        return assertPojoMethodsFor(clazz, predicateAcceptingAllFields);
    }

    public static AbstractAssetion assertPojoMethodsFor(final Class<?> clazz, final Predicate<String> fieldPredicate) {
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(clazz, fieldPredicate);
        return assertPojoMethodsFor(classAndFieldPredicatePair);
    }

    public static AbstractAssetion assertPojoMethodsFor(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                                                        final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        return new SingleClassAssetion(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs);
    }

    public static AbstractAssetion assertPojoMethodsForAll(final Class... classes) {
        final ClassAndFieldPredicatePair[] classesAndFieldPredicatesPairs = Arrays.stream(classes)
                                                                                  .map(ClassAndFieldPredicatePair::new)
                                                                                  .toArray(ClassAndFieldPredicatePair[]::new);
        return assertPojoMethodsForAll(classesAndFieldPredicatesPairs);
    }

    public static AbstractAssetion assertPojoMethodsForAll(final ClassAndFieldPredicatePair... classesAndFieldPredicatesPairs) {
        final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs = Arrays.asList(classesAndFieldPredicatesPairs);
        return new MultiClassAssetion(classAndFieldPredicatePairs);
    }

}
