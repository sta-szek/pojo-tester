package org.pojo.tester;

import org.pojo.tester.assertion.Assertions;
import org.pojo.tester.field.FieldsValuesChanger;
import org.pojo.tester.field.primitive.PrimitiveValueChanger;

import java.util.Arrays;

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

    private void killEveryone(Object o) {
        o = null;
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
                  .isReflexive();
        assertions.assertThat(object)
                  .isReflexive();
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

    private void shouldNotEqualDifferentObjectWithSameTypeAndDifferentFieldsValues(final Object object) {
        final Object objectToCompare = createInstanceWithDifferentFieldValues(object);
        assertions.assertThat(object)
                  .isNotEqualTo(objectToCompare);
    }

    private Object createInstanceWithDifferentFieldValues(final Object object) {
        final Object otherObject = createInstance(object.getClass());

        if (fieldsValuesChanger != null) {
            fieldsValuesChanger.changeFieldsValues(object, otherObject);
        }

        return otherObject;
    }

}