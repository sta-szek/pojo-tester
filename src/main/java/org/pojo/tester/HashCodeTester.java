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

public class HashCodeTester {

    private final Assertions assertions = new Assertions();
    private final ObjectGenerator objectGenerator;

    public HashCodeTester() {
        this(AbstractPrimitiveValueChanger.getInstance());
    }

    public HashCodeTester(final AbstractFieldsValuesChanger abstractFieldsValuesChanger) {
        objectGenerator = new ObjectGenerator(abstractFieldsValuesChanger);
    }

    public void testHashCodeMethod(final Class<?> clazz, final Predicate<String> fieldPredicate) {
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(clazz, fieldPredicate);
        testHashCodeMethod(classAndFieldPredicatePair);
    }

    public void testHashCodeMethod(final Class... classes) {
        Arrays.stream(classes)
              .map(ClassAndFieldPredicatePair::new)
              .forEach(this::testHashCodeMethod);
    }

    private void testHashCodeMethod(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Class<?> testedClass = classAndFieldPredicatePair.getTestedClass();
        final Object instance = objectGenerator.createNewInstance(testedClass);
        final List<Field> allFields = FieldUtils.getFields(testedClass, classAndFieldPredicatePair.getPredicate());

        shouldHaveSameHashCodes(instance);
        shouldHaveSameHashCodesWithDifferentInstance(instance);
        shouldHaveDifferentHashCodes(instance, allFields);

        assertions.assertAll();
    }

    private void shouldHaveSameHashCodes(final Object object) {
        assertions.assertThatHashCodeMethod(object)
                  .isConsistent();
    }

    private void shouldHaveSameHashCodesWithDifferentInstance(final Object object) {
        final Object otherObject = objectGenerator.createSameInstance(object);
        assertions.assertThatHashCodeMethod(object)
                  .returnsSameValueFor(otherObject);
    }


    private void shouldHaveDifferentHashCodes(final Object instance, final List<Field> fields) {
        final List<List<Field>> permutationFields = FieldUtils.permutations(fields);
        permutationFields.stream()
                         .map(testedFields -> objectGenerator.createInstanceWithDifferentFieldValues(instance, testedFields))
                         .forEach(assertHaveDifferentHashCodes(instance));
    }

    private Consumer<Object> assertHaveDifferentHashCodes(final Object object) {
        return eachDifferentObject -> assertions.assertThatHashCodeMethod(object)
                                                .returnsDifferentValueFor(eachDifferentObject);
    }


}
