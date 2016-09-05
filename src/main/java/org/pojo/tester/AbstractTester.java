package org.pojo.tester;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import org.pojo.tester.assertion.Assertions;
import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.field.DefaultFieldValueChanger;
import org.pojo.tester.instantiator.ObjectGenerator;


public abstract class AbstractTester {

    protected final Assertions assertions = new Assertions();
    protected final ObjectGenerator objectGenerator;

    public AbstractTester() {
        this(DefaultFieldValueChanger.INSTANCE);
    }

    public AbstractTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
    }

    public void test(final Class<?> clazz) {
        final Predicate<String> predicateAcceptingAllFields = FieldPredicate.includeAllFields(clazz);
        test(clazz, predicateAcceptingAllFields);
    }

    public void test(final Class<?> clazz, final Predicate<String> fieldPredicate) {
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(clazz, fieldPredicate);
        test(classAndFieldPredicatePair);
    }

    public void testAll(final Class... classes) {
        final ClassAndFieldPredicatePair[] classesAndFieldPredicatesPairs = Arrays.stream(classes)
                                                                                  .map(ClassAndFieldPredicatePair::new)
                                                                                  .toArray(ClassAndFieldPredicatePair[]::new);
        testAll(classesAndFieldPredicatesPairs);

    }

    public void testAll(final ClassAndFieldPredicatePair... classesAndFieldPredicatesPairs) {
        final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs = Arrays.asList(classesAndFieldPredicatesPairs);
        classAndFieldPredicatePairs.forEach(base -> test(base, classesAndFieldPredicatesPairs));
    }

    public abstract void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair... classAndFieldPredicatePairs);

}
