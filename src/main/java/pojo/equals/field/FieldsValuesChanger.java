package pojo.equals.field;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;

@Slf4j
public abstract class FieldsValuesChanger<T> {

    private FieldsValuesChanger fieldsValuesChanger;

    public FieldsValuesChanger register(final FieldsValuesChanger fieldsValuesChanger) {
        if (this.fieldsValuesChanger == null) {
            this.fieldsValuesChanger = fieldsValuesChanger;
        } else {
            this.fieldsValuesChanger.register(fieldsValuesChanger);
        }
        return this;
    }

    public void changeFieldsValues(final Object sourceObject, final Object targetObject) {
        final Field[] declaredFields = targetObject.getClass()
                                                   .getDeclaredFields();
        for (final Field field : declaredFields) {
            checkAndChange(sourceObject, targetObject, field);
        }
        callNextValuesChanger(sourceObject, targetObject);
    }

    public abstract boolean areDifferentValues(T sourceValue, T targetValue);

    protected boolean canChange(final Field field) {
        return !Modifier.isFinal(field.getModifiers());
    }

    protected abstract T increaseValue(T value);

    protected Class getGenericTypeClass() {
        return ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    private void checkAndChange(final Object sourceObject, final Object targetObject, final Field field) {
        if (canChange(field)) {
            changeField(sourceObject, targetObject, field);
        }
    }

    private void callNextValuesChanger(final Object sourceObject, final Object targetObject) {
        if (fieldsValuesChanger != null) {
            fieldsValuesChanger.changeFieldsValues(sourceObject, targetObject);
        }
    }

    private void changeField(final Object sourceObject, final Object targetObject, final Field field) {
        T sourceFieldValue = null;
        T targetFieldValue = null;

        try {
            field.setAccessible(true);
            sourceFieldValue = (T) field.get(sourceObject);
            targetFieldValue = (T) field.get(sourceObject);
        } catch (final IllegalAccessException e) {
            log.error("Cannot access field " + field.getName() + " in object " + sourceObject + " of type " + sourceObject.getClass());
            //TODO ustaw pole na podstawie currentTimeMilis
        }


        if (areDifferentValues(sourceFieldValue, targetFieldValue)) {
            return;
        }
        final T increasedValue = increaseValue(targetFieldValue);
        try {
            field.set(targetObject, increasedValue);
        } catch (final IllegalAccessException e) {
            log.error("Cannot write field " + field.getName() + " in object " + sourceObject + " of type " + sourceObject.getClass());
        }

    }
}
