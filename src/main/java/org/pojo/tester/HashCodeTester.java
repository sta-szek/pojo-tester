package org.pojo.tester;


import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;
import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.utils.FieldUtils;

public class HashCodeTester extends AbstractTester {

    public HashCodeTester() {
        super();
    }

    public HashCodeTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    protected void test(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Class<?> testedClass = classAndFieldPredicatePair.getTestedClass();
        final Object instance = objectGenerator.createNewInstance(testedClass);
        final List<Field> allFields = FieldUtils.getFields(testedClass, classAndFieldPredicatePair.getPredicate());

        shouldHaveSameHashCodes(instance);
        shouldHaveSameHashCodesWithDifferentInstance(instance);
        shouldHaveDifferentHashCodes(instance, allFields);
    }

    private void shouldHaveSameHashCodes(final Object object) {
        assertions.assertThatHashCodeMethodFor(object)
                  .isConsistent();
    }

    private void shouldHaveSameHashCodesWithDifferentInstance(final Object object) {
        final Object otherObject = objectGenerator.createSameInstance(object);
        assertions.assertThatHashCodeMethodFor(object)
                  .returnsSameValueFor(otherObject);
    }


    private void shouldHaveDifferentHashCodes(final Object instance, final List<Field> fields) {
        final List<List<Field>> permutationFields = FieldUtils.permutations(fields);
        permutationFields.stream()
                         .map(testedFields -> objectGenerator.createInstanceWithDifferentFieldValues(instance, testedFields))
                         .forEach(assertHaveDifferentHashCodes(instance));
    }

    private Consumer<Object> assertHaveDifferentHashCodes(final Object object) {
        return eachDifferentObject -> assertions.assertThatHashCodeMethodFor(object)
                                                .returnsDifferentValueFor(eachDifferentObject);
    }


}
