package pojo.equals.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;

public abstract class FieldsValuesChanger<T> {

    private FieldsValuesChanger fieldsValuesChanger;

    public FieldsValuesChanger register(FieldsValuesChanger fieldsValuesChanger) {
        if (this.fieldsValuesChanger == null) {
            this.fieldsValuesChanger = fieldsValuesChanger;
        } else {
            this.fieldsValuesChanger.register(fieldsValuesChanger);
        }
        return this;
    }

    public void changeFieldsValues(Object sourceObject, Object targetObject) {
        Field[] declaredFields = targetObject.getClass()
                                             .getDeclaredFields();
        for (Field field : declaredFields) {
            checkAndChange(sourceObject, targetObject, field);
        }
        callNextValuesChanger(sourceObject, targetObject);
    }

    private void checkAndChange(Object sourceObject, Object targetObject, Field field) {
        if (canChange(field)) {
            changeField(sourceObject, targetObject, field);
        }
    }

    private void callNextValuesChanger(Object sourceObject, Object targetObject) {
        if (fieldsValuesChanger != null) {
            fieldsValuesChanger.changeFieldsValues(sourceObject, targetObject);
        }
    }

    private void changeField(Object sourceObject, Object targetObject, Field field) {
        Object sourceFieldValue = null;
        Object targetFieldValue = null;

        try {
            field.setAccessible(true);
            sourceFieldValue = field.get(sourceObject);
            targetFieldValue = field.get(sourceObject);
        } catch (IllegalAccessException e) {
            System.err.println("Cannot get " + field.getName() + " value from object " + sourceObject + "(" + sourceObject.getClass() + ")");
            //TODO ustaw pole na podstawie currentTimeMilis
        }

        if (sourceFieldValue != null && targetFieldValue != null) {
            T sourceValue = (T) sourceFieldValue;
            T targetValue = (T) targetFieldValue;
            if (areDifferentValues(sourceValue, targetValue)) {
                return;
            }
            T increasedValue = increaseValue(targetValue);
            try {
                field.set(targetObject, increasedValue);
            } catch (IllegalAccessException e) {
                System.err.println("Cannot set " + field.getName() + " value from object " + targetObject + "(" + targetObject.getClass() + ")");
            }
        }
    }

    protected boolean canChange(Field field) {
        return !Modifier.isFinal(field.getModifiers());
    }

    public abstract boolean areDifferentValues(T sourceValue, T targetValue);

    protected abstract T increaseValue(T value);

    protected Class getGenericTypeClass() {
        return ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }
}
