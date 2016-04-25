package org.pojo.tester;

import org.pojo.tester.assertion.Assertions;
import org.pojo.tester.field.AbstractFieldsValuesChanger;
import org.pojo.tester.field.FieldUtils;
import org.pojo.tester.field.primitive.AbstractPrimitiveValueChanger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class EqualsTester {

    private final Assertions assertions = new Assertions();
    private final AbstractFieldsValuesChanger abstractFieldsValuesChanger;

    public EqualsTester() {
        this(AbstractPrimitiveValueChanger.getInstance());
    }

    public EqualsTester(final AbstractFieldsValuesChanger abstractFieldsValuesChanger) {
        this.abstractFieldsValuesChanger = abstractFieldsValuesChanger;
    }

    public void testEqualsIncludingFields(final Class<?> clazz, final List<String> includedFields) {
        testEquals(clazz, includingFields(includedFields));
    }

    public void testEqualsExcludingFields(final Class<?> clazz, final List<String> excludedFields) {
        testEquals(clazz, excludingFields(excludedFields));
    }

    public void testEquals(final Class... classes) {
        Arrays.stream(classes)
              .map(this::toClassWithConsumerAcceptingAllFields)
              .forEach(this::testEquals);
        assertions.assertAll();
    }

    private ClassAndFieldConsumerPair toClassWithConsumerAcceptingAllFields(final Class<?> clazz) {
        final List<String> allFields = FieldUtils.getAllFieldNames(clazz);
        return new ClassAndFieldConsumerPair(clazz, includingFields(allFields));
    }

    private void testEquals(final Class<?> clazz, final Consumer<Object> consumer) {
        final ClassAndFieldConsumerPair classAndFieldConsumerPair = new ClassAndFieldConsumerPair(clazz, consumer);
        testEquals(classAndFieldConsumerPair);
    }

    private void testEquals(final ClassAndFieldConsumerPair classAndFieldConsumerPair) {
        final Object instance = createInstance(classAndFieldConsumerPair.getTestedClass());

        shouldEqualSameObject(instance);
        shouldEqualSameObjectFewTimes(instance);
        shouldEqualDifferentObjectWithSameType(instance);
        shouldEqualObjectCifObjectBisEqualToObjectAndC(instance);
        shouldNotEqualNull(instance);
        shouldNotEqualObjectWithDifferentType(instance);
        classAndFieldConsumerPair.getConsumer()
                                 .accept(instance);

        assertions.assertAll();
    }

    private Consumer<Object> includingFields(final List<String> includedFields) {
        return object -> shouldNotEqualDifferentObjectWithSameTypeAndDifferentFieldsValuesIncludingFields(object, includedFields);
    }

    private Consumer<Object> excludingFields(final List<String> excludedFields) {
        return object -> shouldNotEqualDifferentObjectWithSameTypeAndDifferentFieldsValuesExcludingFields(object, excludedFields);
    }

    private Object createInstance(final Class clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            //TODO zr�b w�asny exception + obs�uz go wyzej
            throw new AssertionError("Unable to create object for class: " + clazz, e);
        }
    }

    private void shouldEqualSameObject(final Object object) {
        assertions.assertThat(object)
                  .isReflexive();
    }

    private void shouldEqualSameObjectFewTimes(final Object object) {
        assertions.assertThat(object)
                  .isConsistent();
    }

    private void shouldEqualDifferentObjectWithSameType(final Object object) {
        final Object otherObject = createInstance(object.getClass());
        assertions.assertThat(object)
                  .isSymmetric(otherObject);
    }

    private void shouldEqualObjectCifObjectBisEqualToObjectAndC(final Object object) {
        final Class<?> objectClass = object.getClass();
        final Object b = createInstance(objectClass);
        final Object c = createInstance(objectClass);
        assertions.assertThat(object)
                  .isTransitive(b, c);
    }

    private void shouldNotEqualNull(final Object object) {
        assertions.assertThat(object)
                  .isNotEqualToNull();
    }

    private void shouldNotEqualObjectWithDifferentType(final Object object) {
        final Object objectToCompare = this;
        assertions.assertThat(object)
                  .isNotEqualToObjectWithDifferentType(objectToCompare);
    }

    private void shouldNotEqualDifferentObjectWithSameTypeAndDifferentFieldsValuesExcludingFields(final Object baseObject,
                                                                                                  final List<String> excludedFields) {
        final List<Field> allFields = FieldUtils.getAllFieldsExcluding(baseObject.getClass(), excludedFields);
        shouldNotEqualDifferentObjectWithSameTypeAndDifferentFieldsValuesWithSpecifiedFields(baseObject, allFields);
    }

    private void shouldNotEqualDifferentObjectWithSameTypeAndDifferentFieldsValuesIncludingFields(final Object baseObject,
                                                                                                  final List<String> includedFields) {
        final List<Field> allFields = FieldUtils.getSpecifiedFields(baseObject.getClass(), includedFields);
        shouldNotEqualDifferentObjectWithSameTypeAndDifferentFieldsValuesWithSpecifiedFields(baseObject, allFields);
    }

    private void shouldNotEqualDifferentObjectWithSameTypeAndDifferentFieldsValuesWithSpecifiedFields(final Object baseObject,
                                                                                                      final List<Field> specifiedFields) {
        final List<List<Field>> permutationFields = FieldUtils.permutations(specifiedFields);
        permutationFields.stream()
                         .map(fields -> createInstanceWithDifferentFieldValues(baseObject, fields))
                         .forEach(assertIsNotEqualTo(baseObject));
    }

    private Consumer<Object> assertIsNotEqualTo(final Object object) {
        return eachDifferentObject -> assertions.assertThat(object)
                                                .isNotEqualTo(eachDifferentObject);
    }

    private Object createInstanceWithDifferentFieldValues(final Object object, final List<Field> fieldsToChange) {
        final Object otherObject = createInstance(object.getClass());
        abstractFieldsValuesChanger.changeFieldsValues(object, otherObject, fieldsToChange);

        return otherObject;
    }

    private static class ClassAndFieldConsumerPair {

        private final Class<?> testedClass;
        private final Consumer<Object> consumer;

        ClassAndFieldConsumerPair(final Class<?> testedClass, final Consumer<Object> consumer) {
            this.testedClass = testedClass;
            this.consumer = consumer;
        }

        Class<?> getTestedClass() {
            return testedClass;
        }


        Consumer<Object> getConsumer() {
            return consumer;
        }

    }

}
