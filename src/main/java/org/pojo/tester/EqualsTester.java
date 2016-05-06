package org.pojo.tester;

import org.pojo.tester.assertion.Assertions;
import org.pojo.tester.field.AbstractFieldsValuesChanger;
import org.pojo.tester.field.FieldUtils;
import org.pojo.tester.field.primitive.AbstractPrimitiveValueChanger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EqualsTester {

    private final Assertions assertions = new Assertions();
    private final ObjectGenerator objectGenerator;

    public EqualsTester() {
        this(AbstractPrimitiveValueChanger.getInstance());
    }

    public EqualsTester(final AbstractFieldsValuesChanger abstractFieldsValuesChanger) {
        objectGenerator = new ObjectGenerator(abstractFieldsValuesChanger);
    }

    public void testEqualsMethod(final Class<?> clazz, final Predicate<String> fieldPredicate) {
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(clazz, fieldPredicate);
        testEqualsMethod(classAndFieldPredicatePair);
    }

    public void testEqualsMethod(final Class... classes) {
        Arrays.stream(classes)
              .map(ClassAndFieldPredicatePair::new)
              .forEach(this::testEqualsMethod);
    }


    private void testEqualsMethod(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
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

        assertions.assertAll();
    }


    private void shouldEqualSameInstance(final Object object) {
        assertions.assertThatEqualsMethod(object)
                  .isReflexive();
    }

    private void shouldEqualSameInstanceFewTimes(final Object object) {
        assertions.assertThatEqualsMethod(object)
                  .isConsistent();
    }

    private void shouldEqualDifferentInstance(final Object object) {
        final Object otherObject = objectGenerator.createSameInstance(object);
        assertions.assertThatEqualsMethod(object)
                  .isSymmetric(otherObject);
    }

    private void shouldEqualObjectCifObjectBisEqualToObjectAndC(final Object object) {
        final Object b = objectGenerator.createSameInstance(object);
        final Object c = objectGenerator.createSameInstance(object);
        assertions.assertThatEqualsMethod(object)
                  .isTransitive(b, c);
    }

    private void shouldNotEqualNull(final Object object) {
        assertions.assertThatEqualsMethod(object)
                  .isNotEqualToNull();
    }

    private void shouldNotEqualDifferentType(final Object object) {
        final Object objectToCompare = this;
        assertions.assertThatEqualsMethod(object)
                  .isNotEqualToObjectWithDifferentType(objectToCompare);
    }

    private void shouldNotEqualWithGivenFields(final Object baseObject, final List<Field> specifiedFields) {
        final List<List<Field>> permutationFields = FieldUtils.permutations(specifiedFields);
        permutationFields.stream()
                         .map(fields -> objectGenerator.createInstanceWithDifferentFieldValues(baseObject, fields))
                         .forEach(assertIsNotEqualTo(baseObject));
    }

    private Consumer<Object> assertIsNotEqualTo(final Object object) {
        return eachDifferentObject -> assertions.assertThatEqualsMethod(object)
                                                .isNotEqualTo(eachDifferentObject);
    }

}
