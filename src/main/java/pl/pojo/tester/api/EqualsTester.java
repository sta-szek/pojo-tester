package pl.pojo.tester.api;

import java.util.List;
import java.util.function.Consumer;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;


public class EqualsTester extends AbstractTester {

    EqualsTester() {
        super();
    }

    EqualsTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Class<?> testedClass = baseClassAndFieldPredicatePair.getClazz();
        final Object instance = objectGenerator.createNewInstance(testedClass);

        shouldEqualSameInstance(instance);
        shouldEqualSameInstanceFewTimes(instance);
        shouldEqualDifferentInstance(instance);
        shouldEqualObjectCifObjectBisEqualToObjectAndC(instance);
        shouldNotEqualNull(instance);
        shouldNotEqualDifferentType(instance);
        shouldNotEqualWithGivenFields(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs);
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
        final List<Object> differentObjects = objectGenerator.generateDifferentObjects(baseClassAndFieldPredicatePair, classAndFieldPredicatePairs);
        final Object firstObject = differentObjects.remove(0);
        differentObjects.forEach(assertIsNotEqualTo(firstObject));
    }

    private Consumer<Object> assertIsNotEqualTo(final Object object) {
        return eachDifferentObject -> testAssertions.assertThatEqualsMethodFor(object)
                                                    .isNotEqualTo(eachDifferentObject);
    }

}
