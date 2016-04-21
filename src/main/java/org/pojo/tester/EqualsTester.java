package org.pojo.tester;

import org.pojo.tester.assertion.Assertions;
import org.pojo.tester.field.FieldUtils;
import org.pojo.tester.field.FieldsValuesChanger;
import org.pojo.tester.field.primitive.PrimitiveValueChanger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class EqualsTester {

    private final Assertions assertions = new Assertions();
    private FieldsValuesChanger fieldsValuesChanger;

    public EqualsTester() {
        try {
            fieldsValuesChanger = PrimitiveValueChanger.instance();
            //TODO zaloguj wyjątek, pomi� test jezeli si� nie uda
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void testEquals(final Class... classes) {
        Arrays.stream(classes)
              .map(this::createInstance)
              .peek(this::shouldEqualSameObject)
              .peek(this::shouldEqualSameObjectFewTimes)
              .peek(this::shouldEqualDifferentObjectWithSameType)
              .peek(this::shouldEqualObjectCifObjectBisEqualToObjectAndC)
              .peek(this::shouldNotEqualNull)
              .peek(this::shouldNotEqualObjectWithDifferentType)
              .peek(this::shouldNotEqualDifferentObjectWithSameTypeAndDifferentFieldsValues)
              .forEach(this::killEveryone);
        assertions.assertAll();
    }

    private void killEveryone(Object object) {
        object = null;
    }

    private Object createInstance(final Class clazz) {
        final Object object;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            //TODO zr�b w�asny exception + obs�uz go wyzej
            throw new AssertionError("Unable to create object for class: " + clazz);
        }
        return object;
    }

    private void shouldEqualSameObject(final Object object) {
        assertions.assertThat(object)
                  .isReflexive();
    }

    private void shouldEqualSameObjectFewTimes(final Object object) {
        assertions.assertThat(object)
                  .isConsistent();
    }

    private void shouldEqualDifferentObjectWithSameType(final Object object) {
        final Object otherObject = createInstance(object.getClass());
        assertions.assertThat(object)
                  .isSymmetric(otherObject);
    }

    private void shouldEqualObjectCifObjectBisEqualToObjectAndC(final Object object) {
        final Class<?> objectClass = object.getClass();
        final Object b = createInstance(objectClass);
        final Object c = createInstance(objectClass);
        assertions.assertThat(object)
                  .isTransitive(b, c);
    }

    private void shouldNotEqualNull(final Object object) {
        assertions.assertThat(object)
                  .isNotEqualToNull();
    }

    private void shouldNotEqualObjectWithDifferentType(final Object object) {
        final Object objectToCompare = this;
        assertions.assertThat(object)
                  .isNotEqualToObjectWithDifferentType(objectToCompare);
    }

    private void shouldNotEqualDifferentObjectWithSameTypeAndDifferentFieldsValues(final Object baseObject) {
        final List<Field> allFields = FieldUtils.getAllFields(baseObject.getClass());
        final List<List<Field>> permutationFields = FieldUtils.permutations(allFields);
        permutationFields.stream()
                         .map(fields -> createInstanceWithDifferentFieldValues(baseObject, fields))
                         .forEach(assertIsNotEqualTo(baseObject));
    }

    private Consumer<Object> assertIsNotEqualTo(final Object object) {
        return eachDifferentObject -> assertions.assertThat(object)
                                                .isNotEqualTo(eachDifferentObject);
    }

    private Object createInstanceWithDifferentFieldValues(final Object object, final List<Field> fieldsToChange) {
        final Object otherObject = createInstance(object.getClass());
        fieldsValuesChanger.changeFieldsValues(object, otherObject, fieldsToChange);

        return otherObject;
    }

}
