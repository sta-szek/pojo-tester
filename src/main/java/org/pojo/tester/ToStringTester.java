package org.pojo.tester;


import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.field.FieldUtils;
import org.pojo.tester.field.GetValueException;

public class ToStringTester extends AbstractTester {

    public ToStringTester() {
        super();
    }

    public ToStringTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    protected void test(final AbstractTester.ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Class<?> testedClass = classAndFieldPredicatePair.getTestedClass();
        final Object instance = objectGenerator.createNewInstance(testedClass);

        final List<Field> includedFields = getIncludedFields(classAndFieldPredicatePair);
        shouldContainValues(instance, includedFields);

        final List<Field> excludedFields = getExcludedFields(classAndFieldPredicatePair);
        shouldNotContainValues(instance, excludedFields);
    }

    private List<Field> getIncludedFields(final AbstractTester.ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Class<?> testedClass = classAndFieldPredicatePair.getTestedClass();
        return FieldUtils.getFields(testedClass, classAndFieldPredicatePair.getPredicate());
    }

    private List<Field> getExcludedFields(final AbstractTester.ClassAndFieldPredicatePair classAndFieldPredicatePair) {
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
                assertions.assertThatToStringMethodFor(instance)
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
                assertions.assertThatToStringMethodFor(instance)
                          .doestNotContain(fieldName, value);
            } catch (final IllegalAccessException e) {
                throw new GetValueException(fieldName, instance.getClass(), e);
            }
        };
    }


}
