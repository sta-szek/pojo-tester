package pl.pojo.tester.internal.instantiator;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.api.GetOrSetValueException;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.utils.FieldUtils;

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

    public List<Object> generateDifferentObjects(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                                                 final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Map<Class<?>, Predicate<String>> userDefinedClassAndFieldPredicatePairsMap = convertToMap(classAndFieldPredicatePairs);
        final Map<Class<?>, List<Object>> dejaVu = new HashMap<>();

        final Class baseClass = baseClassAndFieldPredicatePair.getClazz();
        final Predicate<String> baseClassFieldPredicate = baseClassAndFieldPredicatePair.getFieldsPredicate();

        final List<Field> baseClassFieldsToChange = FieldUtils.getFields(baseClass, baseClassFieldPredicate);
        userDefinedClassAndFieldPredicatePairsMap.put(baseClass, baseClassFieldPredicate);

        final Map<Class<?>, List<Field>> userDefinedClassAndFieldToChangePairsMap = convertToClassAndFieldsToChange(userDefinedClassAndFieldPredicatePairsMap);

        final List<List<Field>> baseObjectFieldsPermutations = FieldUtils.permutations(baseClassFieldsToChange);

        final Object baseObject = createNewInstance(baseClass);
        final LinkedList<Object> result = new LinkedList<>();
        result.add(baseObject);

        for (final List<Field> eachBaseObjectFieldsPermutation : baseObjectFieldsPermutations) {
            final Object baseObjectCopy = createNewInstance(baseClass);

            final Map<Field, List<Object>> nestedObjectsThatAreWaitingForSetInBaseObjectCopy = new HashMap<>();
            List<Object> partialResult = new ArrayList<>();

            for (final Field permutationField : eachBaseObjectFieldsPermutation) {
                final Class<?> permutationFieldType = permutationField.getType();

                final List<Field> nestedFieldsToChangeInFieldType = userDefinedClassAndFieldToChangePairsMap.get(permutationFieldType);

                if (nestedFieldsToChangeInFieldType == null || permutationFieldType.equals(baseClass)) {
                    final Object newFieldTypeInstance = createNewInstance(permutationFieldType);
                    FieldUtils.setValue(baseObjectCopy, permutationField, newFieldTypeInstance);
                } else {
                    final List<Object> nestedObjectsOfFieldType;
                    if (dejaVu.containsKey(permutationFieldType)) {
                        nestedObjectsOfFieldType = new ArrayList<>(dejaVu.get(permutationFieldType));
                    } else {
                        final Predicate<String> fieldPredicate = userDefinedClassAndFieldPredicatePairsMap.get(permutationFieldType);
                        final List<Field> fieldClassFields = FieldUtils.getFields(permutationFieldType, fieldPredicate);

                        if (hasNestedFieldsToChange(fieldClassFields, userDefinedClassAndFieldPredicatePairsMap)) {
                            final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(permutationFieldType, fieldPredicate);
                            nestedObjectsOfFieldType = generateDifferentObjects(classAndFieldPredicatePair, classAndFieldPredicatePairs);
                        } else {
                            nestedObjectsOfFieldType = generateDifferentObjects(permutationFieldType, fieldClassFields);
                        }
                        dejaVu.putIfAbsent(permutationFieldType, nestedObjectsOfFieldType);
                    }
                    nestedObjectsThatAreWaitingForSetInBaseObjectCopy.put(permutationField, nestedObjectsOfFieldType);
                }
            }

            partialResult.add(baseObjectCopy);
            for (final Map.Entry<Field, List<Object>> nestedObjectsToSet : nestedObjectsThatAreWaitingForSetInBaseObjectCopy.entrySet()) {
                partialResult = createCopiesAndFillThem(partialResult, nestedObjectsToSet);
            }
            result.addAll(partialResult);
        }
        return result;
    }

    Object generateInstanceWithDifferentFieldValues(final Object baseObject, final List<Field> fieldsToChange) {
        final Object objectToChange = generateSameInstance(baseObject);
        abstractFieldValueChanger.changeFieldsValues(baseObject, objectToChange, fieldsToChange);

        return objectToChange;
    }

    private List<Object> generateDifferentObjects(final Class<?> clazz, final List<Field> fieldsToChange) {
        final List<Object> differentObjects;
        final List<List<Field>> permutationOfFields = FieldUtils.permutations(fieldsToChange);
        final Object fieldObject = createNewInstance(clazz);

        differentObjects = permutationOfFields.stream()
                                              .map(fields -> generateInstanceWithDifferentFieldValues(fieldObject, fields))
                                              .collect(Collectors.toList());
        differentObjects.add(0, fieldObject);
        return differentObjects;
    }

    private List<Object> createCopiesAndFillThem(final List<Object> baseObjects, final Map.Entry<Field, List<Object>> nestedObjectsToSet) {
        final List<Object> result = new ArrayList<>();
        final Field fieldToFill = nestedObjectsToSet.getKey();
        final List<Object> objectsToFillWith = nestedObjectsToSet.getValue();

        for (final Object baseObject : baseObjects) {
            final List<Object> baseObjectClones = createCopies(baseObject, objectsToFillWith.size());

            for (int i = 0; i < baseObjectClones.size(); i++) {
                final Object baseObjectClone = baseObjectClones.get(i);
                final Object valueToSet = objectsToFillWith.get(i);
                FieldUtils.setValue(baseObjectClone, fieldToFill, valueToSet);
            }
            result.addAll(baseObjectClones);
        }
        return result;
    }

    private Boolean hasNestedFieldsToChange(final List<Field> fields, final Map<Class<?>, Predicate<String>> classes) {
        return fields.parallelStream()
                     .map(Field::getType)
                     .map(classes::get)
                     .filter(Objects::nonNull)
                     .findAny()
                     .map(anyValue -> true)
                     .orElse(false);
    }

    private List<Object> createCopies(final Object baseObject, final int size) {
        return IntStream.range(0, size)
                        .mapToObj(each -> generateSameInstance(baseObject))
                        .collect(Collectors.toList());
    }

    private Map<Class<?>, List<Field>> convertToClassAndFieldsToChange(final Map<Class<?>, Predicate<String>> classAndFieldPredicatePairMap) {
        return classAndFieldPredicatePairMap.entrySet()
                                            .stream()
                                            .collect(Collectors.toMap(Map.Entry::getKey,
                                                                      entry -> FieldUtils.getFields(entry.getKey(), entry.getValue())));
    }

    private Map<Class<?>, Predicate<String>> convertToMap(final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs) {
        return Stream.of(classAndFieldPredicatePairs)
                     .collect(Collectors.toMap(ClassAndFieldPredicatePair::getClazz, ClassAndFieldPredicatePair::getFieldsPredicate));
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
