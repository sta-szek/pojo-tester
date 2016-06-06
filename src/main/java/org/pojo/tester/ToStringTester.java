package org.pojo.tester;


import org.pojo.tester.assertion.Assertions;
import org.pojo.tester.field.AbstractFieldsValuesChanger;
import org.pojo.tester.field.DefaultFieldsValuesChanger;
import org.pojo.tester.field.FieldUtils;
import org.pojo.tester.field.GetValueException;
import org.pojo.tester.instantiator.ObjectGenerator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ToStringTester {

    private final Assertions assertions = new Assertions();
    private final ObjectGenerator objectGenerator;

    public ToStringTester() {
        this(DefaultFieldsValuesChanger.INSTANCE);
    }

    public ToStringTester(final AbstractFieldsValuesChanger abstractFieldsValuesChanger) {
        objectGenerator = new ObjectGenerator(abstractFieldsValuesChanger);
    }

    public void testToStringMethod(final Class<?> clazz, final Predicate<String> fieldPredicate) {
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(clazz, fieldPredicate);
        testToStringMethod(classAndFieldPredicatePair);
    }

    public void testToStringMethod(final Class... classes) {
        Arrays.stream(classes)
              .map(ClassAndFieldPredicatePair::new)
              .forEach(this::testToStringMethod);
    }

    private void testToStringMethod(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Class<?> testedClass = classAndFieldPredicatePair.getTestedClass();
        final Object instance = objectGenerator.createNewInstance(testedClass);

        final List<Field> includedFields = getIncludedFields(classAndFieldPredicatePair);
        shouldContainValues(instance, includedFields);

        final List<Field> excludedFields = getExcludedFields(classAndFieldPredicatePair);
        shouldNotContainValues(instance, excludedFields);

        assertions.assertAll();
    }

    private List<Field> getIncludedFields(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Class<?> testedClass = classAndFieldPredicatePair.getTestedClass();
        return FieldUtils.getFields(testedClass, classAndFieldPredicatePair.getPredicate());
    }

    private List<Field> getExcludedFields(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final List<Field> includedFields = getIncludedFields(classAndFieldPredicatePair);
        final List<String> included = includedFields.stream()
                                                    .map(Field::getName)
                                                    .collect(Collectors.toList());
        return FieldUtils.getAllFieldsExcluding(classAndFieldPredicatePair.getTestedClass(), included);
    }

    private void shouldContainValues(final Object instance, final List<Field> fields) {
        fields.forEach(assertThatToStringContainsValue(instance));
    }

    private void shouldNotContainValues(final Object instance, final List<Field> fields) {
        fields.forEach(assertThatToStringDoesNotContainValue(instance));
    }

    private Consumer<Field> assertThatToStringContainsValue(final Object instance) {
        return field -> {
            final String fieldName = field.getName();
            try {
                final Object value = FieldUtils.getValue(instance, field);
                assertions.assertThatToStringMethod(instance)
                          .contains(fieldName, value);
            } catch (final IllegalAccessException e) {
                throw new GetValueException(fieldName, instance.getClass(), e);
            }
        };
    }

    private Consumer<Field> assertThatToStringDoesNotContainValue(final Object instance) {
        return field -> {
            final String fieldName = field.getName();
            try {
                final Object value = FieldUtils.getValue(instance, field);
                assertions.assertThatToStringMethod(instance)
                          .doestNotContain(fieldName, value);
            } catch (final IllegalAccessException e) {
                throw new GetValueException(fieldName, instance.getClass(), e);
            }
        };
    }


}
