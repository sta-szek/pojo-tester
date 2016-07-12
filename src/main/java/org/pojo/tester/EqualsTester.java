package org.pojo.tester;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;
import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.utils.FieldUtils;


public class EqualsTester extends AbstractTester {

    public EqualsTester() {
        super();
    }

    public EqualsTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    protected void test(final AbstractTester.ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Class<?> testedClass = classAndFieldPredicatePair.getTestedClass();
        final Object instance = objectGenerator.createNewInstance(testedClass);
        final List<Field> allFields = FieldUtils.getFields(testedClass, classAndFieldPredicatePair.getPredicate());

        shouldEqualSameInstance(instance);
        shouldEqualSameInstanceFewTimes(instance);
        shouldEqualDifferentInstance(instance);
        shouldEqualObjectCifObjectBisEqualToObjectAndC(instance);
        shouldNotEqualNull(instance);
        shouldNotEqualDifferentType(instance);
        shouldNotEqualWithGivenFields(instance, allFields);
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
        final Object otherObject = objectGenerator.createSameInstance(object);
        assertions.assertThatEqualsMethodFor(object)
                  .isSymmetric(otherObject);
    }

    private void shouldEqualObjectCifObjectBisEqualToObjectAndC(final Object object) {
        final Object b = objectGenerator.createSameInstance(object);
        final Object c = objectGenerator.createSameInstance(object);
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

    private void shouldNotEqualWithGivenFields(final Object baseObject, final List<Field> specifiedFields) {
        final List<List<Field>> permutationFields = FieldUtils.permutations(specifiedFields);
        permutationFields.stream()
                         .map(fields -> objectGenerator.createInstanceWithDifferentFieldValues(baseObject, fields))
                         .forEach(assertIsNotEqualTo(baseObject));
    }

    private Consumer<Object> assertIsNotEqualTo(final Object object) {
        return eachDifferentObject -> assertions.assertThatEqualsMethodFor(object)
                                                .isNotEqualTo(eachDifferentObject);
    }

}
