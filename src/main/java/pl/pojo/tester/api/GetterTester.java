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

public class GetterTester extends AbstractTester {

    GetterTester() {
        super();
    }

    GetterTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Class testedClass = baseClassAndFieldPredicatePair.getClazz();
        final List<Field> fields = FieldUtils.getFields(testedClass, baseClassAndFieldPredicatePair.getFieldsPredicate());
        final List<GetterAndFieldPair> getterAndFieldPairs = findGettersForFields(testedClass, fields);
        final Object instance = objectGenerator.createNewInstance(testedClass);

        getterAndFieldPairs.forEach(eachPair -> testGetter(eachPair, instance));
    }

    private void testGetter(final GetterAndFieldPair eachPair, final Object instance) {
        final Method getter = eachPair.getGetter();
        final Field field = eachPair.getField();
        try {
            testAssertions.assertThatGetMethodFor(instance)
                          .willGetValueFromField(getter, field);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new GetOrSetValueException(field.getName(), instance.getClass(), e);
        }
    }

    private List<GetterAndFieldPair> findGettersForFields(final Class<?> testedClass, final List<Field> fields) {
        return fields.stream()
                     .map(fieldName -> findSetterAndGetterPairForField(testedClass, fieldName))
                     .collect(Collectors.toList());
    }

    private GetterAndFieldPair findSetterAndGetterPairForField(final Class<?> testedClass, final Field field) {
        final Method getter = MethodUtils.findGetterFor(testedClass, field);
        return new GetterAndFieldPair(getter, field);
    }

    @Getter
    @AllArgsConstructor
    private class GetterAndFieldPair {
        private Method getter;
        private Field field;
    }
}
