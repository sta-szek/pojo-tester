package org.pojo.tester;


import java.util.function.Consumer;
import org.pojo.tester.field.AbstractFieldValueChanger;

public class HashCodeTester extends AbstractTester {

    public HashCodeTester() {
        super();
    }

    public HashCodeTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    protected void test(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Class<?> testedClass = classAndFieldPredicatePair.getClazz();
        final Object instance = objectGenerator.createNewInstance(testedClass);

        shouldHaveSameHashCodes(instance);
        shouldHaveSameHashCodesWithDifferentInstance(instance);
        shouldHaveDifferentHashCodes(classAndFieldPredicatePair);
    }

    private void shouldHaveSameHashCodes(final Object object) {
        assertions.assertThatHashCodeMethodFor(object)
                  .isConsistent();
    }

    private void shouldHaveSameHashCodesWithDifferentInstance(final Object object) {
        final Object otherObject = objectGenerator.generateSameInstance(object);
        assertions.assertThatHashCodeMethodFor(object)
                  .returnsSameValueFor(otherObject);
    }


    private void shouldHaveDifferentHashCodes(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Object baseObject = objectGenerator.createNewInstance(classAndFieldPredicatePair.getClazz());
        objectGenerator.generateDifferentObjectsFrom(baseObject, classAndFieldPredicatePair)
                       .forEach(assertHaveDifferentHashCodes(baseObject));
    }

    private Consumer<Object> assertHaveDifferentHashCodes(final Object object) {
        return eachDifferentObject -> assertions.assertThatHashCodeMethodFor(object)
                                                .returnsDifferentValueFor(eachDifferentObject);
    }

}
