package pl.pojo.tester.internal.tester;


import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.utils.FieldUtils;
import pl.pojo.tester.internal.utils.MethodUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static pl.pojo.tester.internal.utils.FieldUtils.isFinal;

public class SetterTester extends AbstractTester {

    public SetterTester() {
        super();
    }

    public SetterTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                     final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Class testedClass = baseClassAndFieldPredicatePair.getClazz();
        final List<Field> fields = FieldUtils.getFields(testedClass,
                                                        baseClassAndFieldPredicatePair.getFieldsPredicate());
        final List<SetterAndFieldPair> setterAndFieldPairs = findSetterAndGetterPairsForFields(testedClass, fields);
        final Object instance = objectGenerator.createNewInstance(testedClass);

        setterAndFieldPairs.forEach(eachPair -> testSetterAndGetter(eachPair, instance));
    }

    private void testSetterAndGetter(final SetterAndFieldPair eachPair, final Object instance) {
        final Method setter = eachPair.getSetter();
        final Field field = eachPair.getField();
        final Class<?> fieldType = field.getType();
        final Object newValue = objectGenerator.createNewInstance(fieldType);
        testAssertions.assertThatSetMethodFor(instance)
                      .willSetValueOnField(setter, field, newValue);
    }


    private List<SetterAndFieldPair> findSetterAndGetterPairsForFields(final Class<?> testedClass,
                                                                       final List<Field> fields) {
        return fields.stream()
                     .filter(field -> ! isFinal(field))
                     .map(fieldName -> findSetterAndGetterPairForField(testedClass, fieldName))
                     .collect(Collectors.toList());
    }

    private SetterAndFieldPair findSetterAndGetterPairForField(final Class<?> testedClass, final Field field) {
        final Method setter = MethodUtils.findSetterFor(testedClass, field);
        return new SetterAndFieldPair(setter, field);
    }

    private class SetterAndFieldPair {
        private final Method setter;
        private final Field field;

        public SetterAndFieldPair(final Method setter, final Field field) {
            this.setter = setter;
            this.field = field;
        }

        public Method getSetter() {
            return setter;
        }

        public Field getField() {
            return field;
        }
    }
}
