package pl.pojo.tester.api.assertion;


import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.api.FieldPredicate;
import pl.pojo.tester.internal.instantiator.ClassLoader;

import static pl.pojo.tester.internal.preconditions.ParameterPreconditions.checkNotBlank;
import static pl.pojo.tester.internal.preconditions.ParameterPreconditions.checkNotNull;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Assertions {

    public static AbstractAssetion assertPojoMethodsFor(final String qualifiedClassName) {
        checkNotBlank("qualifiedClassName", qualifiedClassName);

        final Class<?> clazz = ClassLoader.loadClass(qualifiedClassName);
        return assertPojoMethodsFor(clazz);
    }

    public static AbstractAssetion assertPojoMethodsFor(final Class<?> clazz) {
        checkNotNull("clazz", clazz);

        final Predicate<String> predicateAcceptingAllFields = FieldPredicate.includeAllFields(clazz);
        return assertPojoMethodsFor(clazz, predicateAcceptingAllFields);
    }

    public static AbstractAssetion assertPojoMethodsFor(final String qualifiedClassName, final Predicate<String> fieldPredicate) {
        checkNotBlank("qualifiedClassName", qualifiedClassName);
        checkNotNull("fieldPredicate", fieldPredicate);

        final Class<?> clazz = ClassLoader.loadClass(qualifiedClassName);
        return assertPojoMethodsFor(clazz, fieldPredicate);
    }

    public static AbstractAssetion assertPojoMethodsFor(final Class<?> clazz, final Predicate<String> fieldPredicate) {
        checkNotNull("clazz", clazz);
        checkNotNull("fieldPredicate", fieldPredicate);

        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(clazz, fieldPredicate);
        return assertPojoMethodsFor(classAndFieldPredicatePair);
    }

    public static AbstractAssetion assertPojoMethodsFor(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                                                        final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        checkNotNull("baseClassAndFieldPredicatePair", baseClassAndFieldPredicatePair);
        return new SingleClassAssetion(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs);
    }

    public static AbstractAssetion assertPojoMethodsForAll(final String... qualifiedClassNames) {
        checkNotBlank("qualifiedClassNames", qualifiedClassNames);

        final Class<?>[] classesAndFieldPredicatesPairs = Arrays.stream(qualifiedClassNames)
                                                                .map(ClassLoader::loadClass)
                                                                .toArray(Class[]::new);
        return assertPojoMethodsForAll(classesAndFieldPredicatesPairs);
    }

    public static AbstractAssetion assertPojoMethodsForAll(final Class... classes) {
        checkNotNull("classes", classes);

        final ClassAndFieldPredicatePair[] classesAndFieldPredicatesPairs = Arrays.stream(classes)
                                                                                  .map(ClassAndFieldPredicatePair::new)
                                                                                  .toArray(ClassAndFieldPredicatePair[]::new);
        return assertPojoMethodsForAll(classesAndFieldPredicatesPairs);
    }


    public static AbstractAssetion assertPojoMethodsForAll(final ClassAndFieldPredicatePair... classesAndFieldPredicatesPairs) {
        checkNotNull("classesAndFieldPredicatesPairs", classesAndFieldPredicatesPairs);

        final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs = Arrays.asList(classesAndFieldPredicatesPairs);
        return new MultiClassAssetion(classAndFieldPredicatePairs);
    }

}
