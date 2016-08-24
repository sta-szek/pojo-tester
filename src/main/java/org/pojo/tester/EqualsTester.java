package org.pojo.tester;

import java.util.List;
import java.util.function.Consumer;
import org.pojo.tester.field.AbstractFieldValueChanger;


public class EqualsTester extends AbstractTester {

    public EqualsTester() {
        super();
    }

    public EqualsTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    protected void test(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Class<?> testedClass = classAndFieldPredicatePair.getClazz();
        final Object instance = objectGenerator.createNewInstance(testedClass);

        shouldEqualSameInstance(instance);
        shouldEqualSameInstanceFewTimes(instance);
        shouldEqualDifferentInstance(instance);
        shouldEqualObjectCifObjectBisEqualToObjectAndC(instance);
        shouldNotEqualNull(instance);
        shouldNotEqualDifferentType(instance);
        shouldNotEqualWithGivenFields(classAndFieldPredicatePair);
    }

    private void shouldEqualSameInstance(final Object object) {
        assertions.assertThatEqualsMethodFor(object)
                  .isReflexive();
    }

    private void shouldEqualSameInstanceFewTimes(final Object object) {
        assertions.assertThatEqualsMethodFor(object)
                  .isConsistent();
    }

    private void shouldEqualDifferentInstance(final Object object) {
        final Object otherObject = objectGenerator.generateSameInstance(object);
        assertions.assertThatEqualsMethodFor(object)
                  .isSymmetric(otherObject);
    }

    private void shouldEqualObjectCifObjectBisEqualToObjectAndC(final Object object) {
        final Object b = objectGenerator.generateSameInstance(object);
        final Object c = objectGenerator.generateSameInstance(object);
        assertions.assertThatEqualsMethodFor(object)
                  .isTransitive(b, c);
    }

    private void shouldNotEqualNull(final Object object) {
        assertions.assertThatEqualsMethodFor(object)
                  .isNotEqualToNull();
    }

    private void shouldNotEqualDifferentType(final Object object) {
        final Object objectToCompare = this;
        assertions.assertThatEqualsMethodFor(object)
                  .isNotEqualToObjectWithDifferentType(objectToCompare);
    }

    private void shouldNotEqualWithGivenFields(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final List<Object> differentObjects = objectGenerator.generateDifferentObjects(classAndFieldPredicatePair);
        final Object firstObject = differentObjects.remove(0);
        differentObjects.forEach(assertIsNotEqualTo(firstObject));
    }

    private Consumer<Object> assertIsNotEqualTo(final Object object) {
        return eachDifferentObject -> assertions.assertThatEqualsMethodFor(object)
                                                .isNotEqualTo(eachDifferentObject);
    }

}
