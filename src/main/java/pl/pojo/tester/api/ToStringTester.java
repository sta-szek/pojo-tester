package pl.pojo.tester.api;


import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.utils.FieldUtils;

/**
 * ToStringTester tests classes if the implementation of {@code toString} method is good.
 *
 * @author Piotr Joński
 * @since 0.1.0
 */
public class ToStringTester extends AbstractTester {

    /**
     * {@inheritDoc}
     */
    public ToStringTester() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public ToStringTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Class<?> testedClass = baseClassAndFieldPredicatePair.getClazz();
        final Object instance = objectGenerator.createNewInstance(testedClass);

        final List<Field> includedFields = getIncludedFields(baseClassAndFieldPredicatePair);
        shouldContainValues(instance, includedFields);

        final List<Field> excludedFields = getExcludedFields(baseClassAndFieldPredicatePair);
        shouldNotContainValues(instance, excludedFields);
    }

    private List<Field> getIncludedFields(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Class<?> testedClass = classAndFieldPredicatePair.getClazz();
        return FieldUtils.getFields(testedClass, classAndFieldPredicatePair.getFieldsPredicate());
    }

    private List<Field> getExcludedFields(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final List<Field> includedFields = getIncludedFields(classAndFieldPredicatePair);
        final List<String> included = includedFields.stream()
                                                    .map(Field::getName)
                                                    .collect(Collectors.toList());
        return FieldUtils.getAllFieldsExcluding(classAndFieldPredicatePair.getClazz(), included);
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
            final Object value = FieldUtils.getValue(instance, field);
            testAssertions.assertThatToStringMethodFor(instance)
                          .contains(fieldName, value);
        };
    }

    private Consumer<Field> assertThatToStringDoesNotContainValue(final Object instance) {
        return field -> {
            final String fieldName = field.getName();
            final Object value = FieldUtils.getValue(instance, field);
            testAssertions.assertThatToStringMethodFor(instance)
                          .doestNotContain(fieldName, value);
        };
    }

}
