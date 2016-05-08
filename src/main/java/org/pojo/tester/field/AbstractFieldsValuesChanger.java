package org.pojo.tester.field;

import lombok.extern.slf4j.Slf4j;
import org.pojo.tester.GetOrSetValueException;

import java.lang.reflect.Field;
import java.util.List;

@Slf4j
public abstract class AbstractFieldsValuesChanger<T> {

    private AbstractFieldsValuesChanger abstractFieldsValuesChanger;

    public AbstractFieldsValuesChanger register(final AbstractFieldsValuesChanger abstractFieldsValuesChanger) {
        if (this.abstractFieldsValuesChanger == null) {
            this.abstractFieldsValuesChanger = abstractFieldsValuesChanger;
        } else {
            this.abstractFieldsValuesChanger.register(abstractFieldsValuesChanger);
        }
        return this;
    }

    public void changeFieldsValues(final Object sourceObject, final Object targetObject, final List<Field> fieldsToChange) {
        fieldsToChange.forEach(eachField -> checkAndChange(sourceObject, targetObject, eachField));
        callNextValuesChanger(sourceObject, targetObject, fieldsToChange);
    }

    public abstract boolean areDifferentValues(T sourceValue, T targetValue);

    protected abstract boolean canChange(final Field field);

    protected abstract T increaseValue(T value, final Class<?> type);

    private void checkAndChange(final Object sourceObject, final Object targetObject, final Field field) {
        if (canChange(field)) {
            changeFieldValue(sourceObject, targetObject, field);
        }
    }

    private void callNextValuesChanger(final Object sourceObject, final Object targetObject, final List<Field> fieldsToChange) {
        if (abstractFieldsValuesChanger != null) {
            abstractFieldsValuesChanger.changeFieldsValues(sourceObject, targetObject, fieldsToChange);
        }
    }

    private void changeFieldValue(final Object sourceObject, final Object targetObject, final Field field) {
        try {
            final T sourceFieldValue = (T) FieldUtils.getValue(sourceObject, field);
            final T targetFieldValue = (T) FieldUtils.getValue(targetObject, field);
            if (!areDifferentValues(sourceFieldValue, targetFieldValue)) {
                final T increasedValue = increaseValue(targetFieldValue, field.getType());
                FieldUtils.setValue(targetObject, field, increasedValue);
            }
        } catch (final IllegalAccessException e) {
            throw new GetOrSetValueException(field.getName(), sourceObject.getClass(), e);
        }

    }
}
