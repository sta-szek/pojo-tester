package pl.pojo.tester.internal.tester;

import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EqualsTester extends AbstractTester {

    public EqualsTester() {
        super();
    }

    public EqualsTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                     final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Class<?> testedClass = baseClassAndFieldPredicatePair.getClazz();
        final Object instance = objectGenerator.createNewInstance(testedClass);

        shouldEqualSameInstance(instance);
        shouldEqualSameInstanceFewTimes(instance);
        shouldEqualDifferentInstance(instance);
        shouldEqualObjectCifObjectBisEqualToObjectAndC(instance);
        shouldNotEqualNull(instance);
        shouldNotEqualDifferentType(instance);
        shouldNotEqualWithGivenFields(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs);
        shouldEqualWithInvertedGivenFields(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs);
    }

    private void shouldEqualSameInstance(final Object object) {
        testAssertions.assertThatEqualsMethodFor(object)
                      .isReflexive();
    }

    private void shouldEqualSameInstanceFewTimes(final Object object) {
        testAssertions.assertThatEqualsMethodFor(object)
                      .isConsistent();
    }

    private void shouldEqualDifferentInstance(final Object object) {
        final Object otherObject = objectGenerator.generateSameInstance(object);
        testAssertions.assertThatEqualsMethodFor(object)
                      .isEqualTo(otherObject);
        testAssertions.assertThatEqualsMethodFor(object)
                      .isSymmetric(otherObject);
    }

    private void shouldEqualObjectCifObjectBisEqualToObjectAndC(final Object object) {
        final Object b = objectGenerator.generateSameInstance(object);
        final Object c = objectGenerator.generateSameInstance(object);
        testAssertions.assertThatEqualsMethodFor(object)
                      .isTransitive(b, c);
    }

    private void shouldNotEqualNull(final Object object) {
        testAssertions.assertThatEqualsMethodFor(object)
                      .isNotEqualToNull();
    }

    private void shouldNotEqualDifferentType(final Object object) {
        final Object objectToCompare = this;
        testAssertions.assertThatEqualsMethodFor(object)
                      .isNotEqualToObjectWithDifferentType(objectToCompare);
    }

    private void shouldNotEqualWithGivenFields(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                                               final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final List<Object> differentObjects = objectGenerator.generateDifferentObjects(baseClassAndFieldPredicatePair,
                                                                                       classAndFieldPredicatePairs);
        final Object firstObject = differentObjects.remove(0);
        differentObjects.forEach(assertIsNotEqualTo(firstObject));
    }

    private Consumer<Object> assertIsNotEqualTo(final Object object) {
        return eachDifferentObject -> testAssertions.assertThatEqualsMethodFor(object)
                                                    .isNotEqualTo(eachDifferentObject);
    }

    private void shouldEqualWithInvertedGivenFields(final ClassAndFieldPredicatePair base,
                                                    final ClassAndFieldPredicatePair... nested) {
        final ClassAndFieldPredicatePair baseWithInvertedFields = invertIncludedFields(base);
        final List<Object> differentObjects = objectGenerator.generateDifferentObjects(baseWithInvertedFields, nested);
        final Object firstObject = differentObjects.remove(0);
        differentObjects.forEach(assertIsEqualTo(firstObject));
    }

    private ClassAndFieldPredicatePair invertIncludedFields(final ClassAndFieldPredicatePair base) {
        final Class<?> clazz = base.getClazz();
        final Predicate<String> excludedFields = base.getFieldsPredicate()
                                                     .negate();
        return new ClassAndFieldPredicatePair(clazz, excludedFields);
    }

    private Consumer<Object> assertIsEqualTo(final Object object) {
        return eachDifferentObject -> testAssertions.assertThatEqualsMethodFor(object)
                                                    .isEqualTo(eachDifferentObject);
    }

}
