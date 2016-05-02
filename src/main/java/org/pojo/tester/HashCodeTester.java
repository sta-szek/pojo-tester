package org.pojo.tester;


import org.pojo.tester.assertion.Assertions;
import org.pojo.tester.field.AbstractFieldsValuesChanger;
import org.pojo.tester.field.FieldUtils;
import org.pojo.tester.field.primitive.AbstractPrimitiveValueChanger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class HashCodeTester {

    private final Assertions assertions = new Assertions();
    private final ObjectGenerator objectGenerator;

    public HashCodeTester() {
        this(AbstractPrimitiveValueChanger.getInstance());
    }

    public HashCodeTester(final AbstractFieldsValuesChanger abstractFieldsValuesChanger) {
        objectGenerator = new ObjectGenerator(abstractFieldsValuesChanger);
    }

    public void testHashCodeIncludingFields(final Class<?> clazz, final List<String> includedFields) {
        testHashCode(clazz, includingFields(includedFields));
    }

    public void testHashCodeExcludingFields(final Class<?> clazz, final List<String> excludedFields) {
        testHashCode(clazz, excludingFields(excludedFields));
    }

    public void testHashCodeIncludingAllFields(final Class... classes) {
        Arrays.stream(classes)
              .map(this::toClassWithConsumerAcceptingAllFields)
              .forEach(this::testHashCode);
    }

    private void testHashCode(final Class<?> clazz, final Consumer<Object> consumer) {
        final ClassAndFieldConsumerPair classAndFieldConsumerPair = new ClassAndFieldConsumerPair(clazz, consumer);
        testHashCode(classAndFieldConsumerPair);
    }

    private void testHashCode(final ClassAndFieldConsumerPair classAndFieldConsumerPair) {
        final Object instance = objectGenerator.createNewInstance(classAndFieldConsumerPair.getTestedClass());

        shouldHaveSameHashCodes(instance);
        shouldHaveSameHashCodesWithDifferentInstance(instance);
        shouldHaveDifferentHashCodes(instance, classAndFieldConsumerPair.getConsumer());

        assertions.assertAll();
    }

    private void shouldHaveDifferentHashCodes(final Object instance, final Consumer<Object> consumer) {
        consumer.accept(instance);
    }

    private ClassAndFieldConsumerPair toClassWithConsumerAcceptingAllFields(final Class<?> clazz) {
        final List<String> allFields = FieldUtils.getAllFieldNames(clazz);
        return new ClassAndFieldConsumerPair(clazz, includingFields(allFields));
    }

    private Consumer<Object> includingFields(final List<String> includedFields) {
        return object -> shouldReturnDifferentHashCodeWithIncludedFields(object, includedFields);
    }

    private Consumer<Object> excludingFields(final List<String> excludedFields) {
        return object -> shouldReturnDifferentHashCodeWithExcludedFields(object, excludedFields);
    }

    private void shouldHaveSameHashCodes(final Object object) {
        assertions.assertThatHashCode(object)
                  .isConsistent();
    }


    private void shouldHaveSameHashCodesWithDifferentInstance(final Object object) {
        final Object otherObject = objectGenerator.createSameInstance(object);
        assertions.assertThatHashCode(object)
                  .isTheSame(otherObject);
    }


    private void shouldReturnDifferentHashCodeWithExcludedFields(final Object baseObject, final List<String> excludedFields) {
        final List<Field> allFields = FieldUtils.getAllFieldsExcluding(baseObject.getClass(), excludedFields);
        shouldReturnDifferentHashCodeWithGivenFields(baseObject, allFields);
    }

    private void shouldReturnDifferentHashCodeWithIncludedFields(final Object baseObject, final List<String> includedFields) {
        final List<Field> allFields = FieldUtils.getSpecifiedFields(baseObject.getClass(), includedFields);
        shouldReturnDifferentHashCodeWithGivenFields(baseObject, allFields);
    }

    private void shouldReturnDifferentHashCodeWithGivenFields(final Object baseObject, final List<Field> specifiedFields) {
        final List<List<Field>> permutationFields = FieldUtils.permutations(specifiedFields);
        permutationFields.stream()
                         .map(fields -> objectGenerator.createInstanceWithDifferentFieldValues(baseObject.getClass(), fields))
                         .forEach(assertHaveDifferentHashCodes(baseObject));
    }

    private Consumer<Object> assertHaveDifferentHashCodes(final Object object) {
        return eachDifferentObject -> assertions.assertThatHashCode(object)
                                                .isDifferentForm(eachDifferentObject);
    }


}
