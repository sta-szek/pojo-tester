package org.pojo.tester.instantiator;

import org.pojo.tester.GetOrSetValueException;
import org.pojo.tester.utils.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;

class SameInstanceGenerator {

    private final NewInstanceGenerator newInstanceGenerator = new NewInstanceGenerator();

    public SameInstanceGenerator() {
    }

    public Object generateSameInstance(final Object object) {
        Object newInstance = newInstanceGenerator.createNewInstance(object.getClass());
        if (!object.equals(newInstance)) {
            newInstance = makeThemEqual(object, newInstance);
        }
        return newInstance;
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
