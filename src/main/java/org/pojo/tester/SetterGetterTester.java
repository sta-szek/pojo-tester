package org.pojo.tester;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.field.FieldUtils;

public class SetterGetterTester extends AbstractTester {

    public SetterGetterTester() {
        super();
    }

    public SetterGetterTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    protected void test(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Class testedClass = classAndFieldPredicatePair.getTestedClass();
        final List<Field> fields = FieldUtils.getFields(testedClass, classAndFieldPredicatePair.getPredicate());
        final List<SetterGetterPair> setterGetterPairs = findSetterAndGetterPairsForFields(testedClass, fields);
        final Object instance = objectGenerator.createNewInstance(testedClass);

        setterGetterPairs.forEach(eachPair -> testSetterAndGetter(eachPair, instance));
    }

    private void testSetterAndGetter(final SetterGetterPair eachPair, final Object instance) {
        final Method setter = eachPair.getSetter();
        final Method getter = eachPair.getGetter();
        final Field field = eachPair.getField();
        final Class<?> fieldType = getter.getReturnType();
        final Object newValue = objectGenerator.createNewInstance(fieldType);

        try {
            assertions.assertThatSetMethodFor(instance)
                      .willSetValueOnField(setter, field, newValue);
            assertions.assertThatGetMethodFor(instance)
                      .willGetValueFromField(getter, field);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new GetOrSetValueException(field.getName(), instance.getClass(), e);
        }
    }

    private List<SetterGetterPair> findSetterAndGetterPairsForFields(final Class<?> testedClass, final List<Field> fields) {
        return fields.stream()
                     .map(fieldName -> findSetterAndGetterPairForField(testedClass, fieldName))
                     .collect(Collectors.toList());
    }

    private SetterGetterPair findSetterAndGetterPairForField(final Class<?> testedClass, final Field field) {
        final Method setter = FieldUtils.findSetterFor(testedClass, field);
        final Method getter = FieldUtils.findGetterFor(testedClass, field);
        return new SetterGetterPair(setter, getter, field);
    }

    @Getter
    @AllArgsConstructor
    private class SetterGetterPair {
        private Method setter;
        private Method getter;
        private Field field;
    }
}
