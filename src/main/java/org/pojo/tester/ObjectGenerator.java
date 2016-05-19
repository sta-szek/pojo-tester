package org.pojo.tester;


import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.pojo.tester.field.AbstractFieldsValuesChanger;
import org.pojo.tester.field.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

class ObjectGenerator {

    private final AbstractFieldsValuesChanger abstractFieldsValuesChanger;

    ObjectGenerator(final AbstractFieldsValuesChanger abstractFieldsValuesChanger) {
        this.abstractFieldsValuesChanger = abstractFieldsValuesChanger;
    }

    Object createSameInstance(final Object object) {
        Object newInstance = createNewInstance(object.getClass());
        if (!object.equals(newInstance)) {
            newInstance = makeThemEqual(object, newInstance);
        }
        return newInstance;
    }

    Object createInstanceWithDifferentFieldValues(final Object baseObject, final List<Field> fieldsToChange) {
        final Object objectToChange = createSameInstance(baseObject);
        abstractFieldsValuesChanger.changeFieldsValues(baseObject, objectToChange, fieldsToChange);

        return objectToChange;
    }

    Object createNewInstance(final Class<?> clazz) {
        try {
            final Objenesis objenesis = new ObjenesisStd();
            final Object newInstance = objenesis.newInstance(clazz);
            abstractFieldsValuesChanger.changeFieldsValues(newInstance, newInstance, FieldUtils.getAllFields(clazz));
            return newInstance;
        } catch (final Throwable t) {
            throw new ObjectInstantiationException(clazz, t);
        }
    }


    private Object makeThemEqual(final Object object, final Object newInstance) {
        String currentFieldName = "";
        try {
            final List<Field> allFields = FieldUtils.getAllFields(object.getClass());
            for (final Field field : allFields) {
                currentFieldName = field.getName();
                final Object value = FieldUtils.getValue(object, field);
                FieldUtils.setValue(newInstance, field, value);
            }
            return newInstance;
        } catch (final IllegalAccessException e) {
            throw new GetOrSetValueException(currentFieldName, object.getClass(), e);
        }
    }

}
