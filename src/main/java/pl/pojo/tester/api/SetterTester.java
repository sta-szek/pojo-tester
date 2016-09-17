package pl.pojo.tester.api;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.utils.FieldUtils;
import pl.pojo.tester.internal.utils.MethodUtils;

@Slf4j
public class SetterTester extends AbstractTester {

    public SetterTester() {
        super();
    }

    public SetterTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Class testedClass = baseClassAndFieldPredicatePair.getClazz();
        final List<Field> fields = FieldUtils.getFields(testedClass, baseClassAndFieldPredicatePair.getFieldsPredicate());
        final List<SetterAndFieldPair> setterAndFieldPairs = findSetterAndGetterPairsForFields(testedClass, fields);
        final Object instance = objectGenerator.createNewInstance(testedClass);

        setterAndFieldPairs.forEach(eachPair -> testSetterAndGetter(eachPair, instance));
    }

    private void testSetterAndGetter(final SetterAndFieldPair eachPair, final Object instance) {
        final Method setter = eachPair.getSetter();
        final Field field = eachPair.getField();
        final Class<?> fieldType = field.getType();
        final Object newValue = objectGenerator.createNewInstance(fieldType);

        try {
            testAssertions.assertThatSetMethodFor(instance)
                          .willSetValueOnField(setter, field, newValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new GetOrSetValueException(field.getName(), instance.getClass(), e);
        }
    }


    private List<SetterAndFieldPair> findSetterAndGetterPairsForFields(final Class<?> testedClass, final List<Field> fields) {
        return fields.stream()
                     .map(fieldName -> findSetterAndGetterPairForField(testedClass, fieldName))
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .collect(Collectors.toList());
    }

    private Optional<SetterAndFieldPair> findSetterAndGetterPairForField(final Class<?> testedClass, final Field field) {
        if (isNotFinal(field)) {
            final Method setter = MethodUtils.findSetterFor(testedClass, field);
            final SetterAndFieldPair setterAndFieldPair = new SetterAndFieldPair(setter, field);
            return Optional.of(setterAndFieldPair);
        } else {
            log.warn("Could not find setter for {}.", field);
            return Optional.empty();
        }
    }

    private boolean isNotFinal(final Field field) {
        return !FieldUtils.isFinal(field);
    }

    @Getter
    @AllArgsConstructor
    private class SetterAndFieldPair {
        private Method setter;
        private Field field;
    }
}
