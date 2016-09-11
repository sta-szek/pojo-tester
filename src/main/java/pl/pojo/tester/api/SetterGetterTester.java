package pl.pojo.tester.api;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.utils.FieldUtils;
import pl.pojo.tester.internal.utils.MethodUtils;

public class SetterGetterTester extends AbstractTester {

    public SetterGetterTester() {
        super();
    }

    public SetterGetterTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Class testedClass = baseClassAndFieldPredicatePair.getClazz();
        final List<Field> fields = FieldUtils.getFields(testedClass, baseClassAndFieldPredicatePair.getFieldsPredicate());
        final List<SetterAndGetterPair> setterAndGetterPairs = findSetterAndGetterPairsForFields(testedClass, fields);
        final Object instance = objectGenerator.createNewInstance(testedClass);

        setterAndGetterPairs.forEach(eachPair -> testSetterAndGetter(eachPair, instance));
    }

    private void testSetterAndGetter(final SetterAndGetterPair eachPair, final Object instance) {
        final Method setter = eachPair.getSetter();
        final Method getter = eachPair.getGetter();
        final Field field = eachPair.getField();
        final Class<?> fieldType = getter.getReturnType();
        final Object newValue = objectGenerator.createNewInstance(fieldType);

        try {
            if (hasSetter(setter)) {
                testAssertions.assertThatSetMethodFor(instance)
                              .willSetValueOnField(setter, field, newValue);
            }
            testAssertions.assertThatGetMethodFor(instance)
                          .willGetValueFromField(getter, field);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new GetOrSetValueException(field.getName(), instance.getClass(), e);
        }
    }

    private boolean hasSetter(final Method setter) {
        return setter != null;
    }

    private boolean isNotFinal(final Field field) {
        return !FieldUtils.isFinal(field);
    }

    private List<SetterAndGetterPair> findSetterAndGetterPairsForFields(final Class<?> testedClass, final List<Field> fields) {
        return fields.stream()
                     .map(fieldName -> findSetterAndGetterPairForField(testedClass, fieldName))
                     .collect(Collectors.toList());
    }

    private SetterAndGetterPair findSetterAndGetterPairForField(final Class<?> testedClass, final Field field) {
        Method setter = null;
        if (isNotFinal(field)) {
            setter = MethodUtils.findSetterFor(testedClass, field);
        }
        final Method getter = MethodUtils.findGetterFor(testedClass, field);
        return new SetterAndGetterPair(setter, getter, field);
    }

    @Getter
    @AllArgsConstructor
    private class SetterAndGetterPair {
        private Method setter;
        private Method getter;
        private Field field;
    }
}
