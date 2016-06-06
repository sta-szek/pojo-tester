package org.pojo.tester.instantiator;


import org.pojo.tester.GetOrSetValueException;
import org.pojo.tester.field.AbstractFieldsValuesChanger;
import org.pojo.tester.field.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

public class ObjectGenerator {

    private final AbstractFieldsValuesChanger abstractFieldsValuesChanger;

    public ObjectGenerator(final AbstractFieldsValuesChanger abstractFieldsValuesChanger) {
        this.abstractFieldsValuesChanger = abstractFieldsValuesChanger;
    }

    public Object createNewInstance(final Class<?> clazz) {
        return Instantiable.forClass(clazz)
                           .instantiate();
    }

    public Object createSameInstance(final Object object) {
        Object newInstance = createNewInstance(object.getClass());
        if (!object.equals(newInstance)) {
            newInstance = makeThemEqual(object, newInstance);
        }
        return newInstance;
    }

    public Object createInstanceWithDifferentFieldValues(final Object baseObject, final List<Field> fieldsToChange) {
        final Object objectToChange = createSameInstance(baseObject);
        abstractFieldsValuesChanger.changeFieldsValues(baseObject, objectToChange, fieldsToChange);

        return objectToChange;
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
