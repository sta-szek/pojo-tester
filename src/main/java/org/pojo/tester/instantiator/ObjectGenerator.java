package org.pojo.tester.instantiator;


import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.pojo.tester.ClassAndFieldPredicatePair;
import org.pojo.tester.GetOrSetValueException;
import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.utils.FieldUtils;

public class ObjectGenerator {

    private final AbstractFieldValueChanger abstractFieldValueChanger;

    public ObjectGenerator(final AbstractFieldValueChanger abstractFieldValueChanger) {
        this.abstractFieldValueChanger = abstractFieldValueChanger;
    }

    public Object createNewInstance(final Class<?> clazz) {
        return Instantiable.forClass(clazz)
                           .instantiate();
    }

    public Object generateSameInstance(final Object object) {
        Object newInstance = createNewInstance(object.getClass());
        if (!object.equals(newInstance)) {
            newInstance = makeThemEqual(object, newInstance);
        }
        return newInstance;
    }

    public List<Object> generateDifferentObjects(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Class clazz = classAndFieldPredicatePair.getClazz();
        final Predicate<String> fieldPredicate = classAndFieldPredicatePair.getFieldsPredicate();

        final List<Field> fieldsToChange = FieldUtils.getFields(clazz, fieldPredicate);
        final List<List<Field>> permutationOfFields = FieldUtils.permutations(fieldsToChange);

        final Object baseObject = createNewInstance(clazz);

        final List<Object> objects = permutationOfFields.stream()
                                                        .map(fields -> generateInstanceWithDifferentFieldValues(baseObject, fields))
                                                        .collect(Collectors.toList());
        objects.add(0, baseObject);
        return objects;
    }

    Object generateInstanceWithDifferentFieldValues(final Object baseObject, final List<Field> fieldsToChange) {
        final Object objectToChange = generateSameInstance(baseObject);
        abstractFieldValueChanger.changeFieldsValues(baseObject, objectToChange, fieldsToChange);

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
