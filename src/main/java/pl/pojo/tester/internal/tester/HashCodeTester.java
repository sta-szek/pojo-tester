package pl.pojo.tester.internal.tester;


import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class HashCodeTester extends AbstractTester {

    public HashCodeTester() {
        super();
    }

    public HashCodeTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                     final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Class<?> testedClass = baseClassAndFieldPredicatePair.getClazz();
        final Object instance = objectGenerator.createNewInstance(testedClass);

        shouldHaveSameHashCodes(instance);
        shouldHaveSameHashCodesWithDifferentInstance(instance);
        shouldHaveDifferentHashCodesForUserDefinedFields(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs);
        shouldHaveSameHashCodesForInversionOfUserDefinedFields(baseClassAndFieldPredicatePair,
                                                               classAndFieldPredicatePairs);
    }

    private void shouldHaveSameHashCodes(final Object object) {
        testAssertions.assertThatHashCodeMethodFor(object)
                      .isConsistent();
    }

    private void shouldHaveSameHashCodesWithDifferentInstance(final Object object) {
        final Object otherObject = objectGenerator.generateSameInstance(object);
        testAssertions.assertThatHashCodeMethodFor(object)
                      .returnsSameValueFor(otherObject);
    }

    private void shouldHaveDifferentHashCodesForUserDefinedFields(final ClassAndFieldPredicatePair base,
                                                                  final ClassAndFieldPredicatePair... nested) {
        final List<Object> differentObjects = objectGenerator.generateDifferentObjects(base, nested);
        final Object firstObject = differentObjects.remove(0);
        differentObjects.forEach(assertHaveDifferentHashCodes(firstObject));
    }


    private Consumer<Object> assertHaveDifferentHashCodes(final Object object) {
        return eachDifferentObject -> testAssertions.assertThatHashCodeMethodFor(object)
                                                    .returnsDifferentValueFor(eachDifferentObject);
    }

    private void shouldHaveSameHashCodesForInversionOfUserDefinedFields(final ClassAndFieldPredicatePair base,
                                                                        final ClassAndFieldPredicatePair... nested) {
        final ClassAndFieldPredicatePair baseWithInvertedFields = invertIncludedFields(base);
        final List<Object> differentObjects = objectGenerator.generateDifferentObjects(baseWithInvertedFields, nested);
        final Object firstObject = differentObjects.remove(0);
        differentObjects.forEach(assertHaveSameHashCodes(firstObject));
    }

    private ClassAndFieldPredicatePair invertIncludedFields(final ClassAndFieldPredicatePair base) {
        final Class<?> clazz = base.getClazz();
        final Predicate<String> excludedFields = base.getFieldsPredicate()
                                                     .negate();
        return new ClassAndFieldPredicatePair(clazz, excludedFields);
    }

    private Consumer<Object> assertHaveSameHashCodes(final Object object) {
        return eachDifferentObject -> testAssertions.assertThatHashCodeMethodFor(object)
                                                    .returnsSameValueFor(eachDifferentObject);
    }

}
