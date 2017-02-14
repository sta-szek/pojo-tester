package pl.pojo.tester.internal.tester;


import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.utils.FieldUtils;
import pl.pojo.tester.internal.utils.MethodUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class GetterTester extends AbstractTester {

    public GetterTester() {
        super();
    }

    public GetterTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                     final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Class testedClass = baseClassAndFieldPredicatePair.getClazz();
        final List<Field> fields = FieldUtils.getFields(testedClass,
                                                        baseClassAndFieldPredicatePair.getFieldsPredicate());
        final List<GetterAndFieldPair> getterAndFieldPairs = findGettersForFields(testedClass, fields);
        final Object instance = objectGenerator.createNewInstance(testedClass);

        getterAndFieldPairs.forEach(eachPair -> testGetter(eachPair, instance));
    }

    private void testGetter(final GetterAndFieldPair eachPair, final Object instance) {
        final Method getter = eachPair.getGetter();
        final Field field = eachPair.getField();
        testAssertions.assertThatGetMethodFor(instance)
                      .willGetValueFromField(getter, field);
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

    private class GetterAndFieldPair {
        private final Method getter;
        private final Field field;

        public GetterAndFieldPair(final Method getter, final Field field) {
            this.getter = getter;
            this.field = field;
        }

        public Method getGetter() {
            return getter;
        }

        public Field getField() {
            return field;
        }
    }
}
