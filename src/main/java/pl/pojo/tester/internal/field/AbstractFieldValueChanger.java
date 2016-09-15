package pl.pojo.tester.internal.field;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import pl.pojo.tester.internal.utils.FieldUtils;

public abstract class AbstractFieldValueChanger<T> {

    private AbstractFieldValueChanger next;

    public void changeFieldsValues(final Object sourceObject, final Object targetObject, final List<Field> fieldsToChange) {
        fieldsToChange.forEach(eachField -> checkAndChange(sourceObject, targetObject, eachField));
        callNextValuesChanger(sourceObject, targetObject, fieldsToChange);
    }

    public abstract boolean areDifferentValues(T sourceValue, T targetValue);

    public AbstractFieldValueChanger attachNext(final AbstractFieldValueChanger abstractFieldValueChanger) {
        if (this.next == null) {
            this.next = abstractFieldValueChanger;
        } else {
            this.next.attachNext(abstractFieldValueChanger);
        }
        return this;
    }

    public T increaseValue(final T value) {
        if (canChange(value.getClass())) {
            return increaseValue(value, value.getClass());
        } else {
            if (next != null) {
                return (T) next.increaseValue(value);
            }
            return value;
        }
    }

    protected abstract boolean canChange(final Class<?> type);

    protected abstract T increaseValue(T value, final Class<?> type);

    protected Class<T> getGenericTypeClass() {
        final Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return type instanceof ParameterizedType
               ? (Class<T>) ((ParameterizedType) type).getRawType()
               : (Class<T>) type;
    }

    private void checkAndChange(final Object sourceObject, final Object targetObject, final Field field) {
        if (canChange(field.getType())) {
            changeFieldValue(sourceObject, targetObject, field);
        }
    }

    private void callNextValuesChanger(final Object sourceObject, final Object targetObject, final List<Field> fieldsToChange) {
        if (next != null) {
            next.changeFieldsValues(sourceObject, targetObject, fieldsToChange);
        }
    }

    private void changeFieldValue(final Object sourceObject, final Object targetObject, final Field field) {
        final T sourceFieldValue = (T) FieldUtils.getValue(sourceObject, field);
        final T targetFieldValue = (T) FieldUtils.getValue(targetObject, field);
        if (!areDifferentValues(sourceFieldValue, targetFieldValue)) {
            final T increasedValue = increaseValue(targetFieldValue, field.getType());
            FieldUtils.setValue(targetObject, field, increasedValue);
        }
    }
}
