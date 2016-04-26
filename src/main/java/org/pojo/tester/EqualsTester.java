package org.pojo.tester;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
    private final ObjectGenerator objectGenerator;

    public EqualsTester() {
        this(AbstractPrimitiveValueChanger.getInstance());
    }

    public EqualsTester(final AbstractFieldsValuesChanger abstractFieldsValuesChanger) {
        objectGenerator = new ObjectGenerator(abstractFieldsValuesChanger);
    }

    public void testEqualsIncludingFields(final Class<?> clazz, final List<String> includedFields) {
        testEquals(clazz, includingFields(includedFields));
    }

    public void testEqualsExcludingFields(final Class<?> clazz, final List<String> excludedFields) {
        testEquals(clazz, excludingFields(excludedFields));
    }

    public void testEqualsIncludingAllFields(final Class... classes) {
        Arrays.stream(classes)
              .map(this::toClassWithConsumerAcceptingAllFields)
              .forEach(this::testEquals);
    }

    private void testEquals(final Class<?> clazz, final Consumer<Object> consumer) {
        final ClassAndFieldConsumerPair classAndFieldConsumerPair = new ClassAndFieldConsumerPair(clazz, consumer);
        testEquals(classAndFieldConsumerPair);
    }

    private void testEquals(final ClassAndFieldConsumerPair classAndFieldConsumerPair) {
        final Object instance = objectGenerator.createNewInstance(classAndFieldConsumerPair.getTestedClass());

        shouldEqualSameInstance(instance);
        shouldEqualSameInstanceFewTimes(instance);
        shouldEqualDifferentInstance(instance);
        shouldEqualObjectCifObjectBisEqualToObjectAndC(instance);
        shouldNotEqualNull(instance);
        shouldNotEqualDifferentType(instance);
        shouldNotEqualSameInstanceWithDifferentValues(instance, classAndFieldConsumerPair.getConsumer());

        assertions.assertAll();
    }

    private void shouldNotEqualSameInstanceWithDifferentValues(final Object instance, final Consumer<Object> consumer) {
        consumer.accept(instance);
    }

    private ClassAndFieldConsumerPair toClassWithConsumerAcceptingAllFields(final Class<?> clazz) {
        final List<String> allFields = FieldUtils.getAllFieldNames(clazz);
        return new ClassAndFieldConsumerPair(clazz, includingFields(allFields));
    }

    private Consumer<Object> includingFields(final List<String> includedFields) {
        return object -> shouldNotEqualWithIncludedFields(object, includedFields);
    }

    private Consumer<Object> excludingFields(final List<String> excludedFields) {
        return object -> shouldNotEqualWithExcludedFields(object, excludedFields);
    }

    private void shouldEqualSameInstance(final Object object) {
        assertions.assertThat(object)
                  .isReflexive();
    }

    private void shouldEqualSameInstanceFewTimes(final Object object) {
        assertions.assertThat(object)
                  .isConsistent();
    }

    private void shouldEqualDifferentInstance(final Object object) {
        final Object otherObject = objectGenerator.createSameInstance(object);
        assertions.assertThat(object)
                  .isSymmetric(otherObject);
    }

    private void shouldEqualObjectCifObjectBisEqualToObjectAndC(final Object object) {
        final Object b = objectGenerator.createSameInstance(object);
        final Object c = objectGenerator.createSameInstance(object);
        assertions.assertThat(object)
                  .isTransitive(b, c);
    }

    private void shouldNotEqualNull(final Object object) {
        assertions.assertThat(object)
                  .isNotEqualToNull();
    }

    private void shouldNotEqualDifferentType(final Object object) {
        final Object objectToCompare = this;
        assertions.assertThat(object)
                  .isNotEqualToObjectWithDifferentType(objectToCompare);
    }

    private void shouldNotEqualWithExcludedFields(final Object baseObject, final List<String> excludedFields) {
        final List<Field> allFields = FieldUtils.getAllFieldsExcluding(baseObject.getClass(), excludedFields);
        shouldNotEqualWithGivenFields(baseObject, allFields);
    }

    private void shouldNotEqualWithIncludedFields(final Object baseObject, final List<String> includedFields) {
        final List<Field> allFields = FieldUtils.getSpecifiedFields(baseObject.getClass(), includedFields);
        shouldNotEqualWithGivenFields(baseObject, allFields);
    }

    private void shouldNotEqualWithGivenFields(final Object baseObject, final List<Field> specifiedFields) {
        final List<List<Field>> permutationFields = FieldUtils.permutations(specifiedFields);
        permutationFields.stream()
                         .map(fields -> objectGenerator.createInstanceWithDifferentFieldValues(baseObject.getClass(), fields))
                         .forEach(assertIsNotEqualTo(baseObject));
    }

    private Consumer<Object> assertIsNotEqualTo(final Object object) {
        return eachDifferentObject -> assertions.assertThat(object)
                                                .isNotEqualTo(eachDifferentObject);
    }


    @Getter
    @AllArgsConstructor
    private static class ClassAndFieldConsumerPair {

        private final Class<?> testedClass;
        private final Consumer<Object> consumer;

    }

}
