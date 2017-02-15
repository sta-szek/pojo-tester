package pl.pojo.tester.internal.instantiator;


import org.apache.commons.collections4.MultiValuedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.api.ConstructorParameters;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.utils.FieldUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ObjectGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectGenerator.class);

    private final AbstractFieldValueChanger abstractFieldValueChanger;
    private final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters;

    public ObjectGenerator(final AbstractFieldValueChanger abstractFieldValueChanger,
                           final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        this.abstractFieldValueChanger = abstractFieldValueChanger;
        this.constructorParameters = constructorParameters;
    }

    public Object createNewInstance(final Class<?> clazz) {
        return Instantiable.forClass(clazz, constructorParameters)
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
        return generateDifferentObjects(0,
                                        new HashMap<>(),
                                        baseClassAndFieldPredicatePair,
                                        classAndFieldPredicatePairs);
    }

    private List<Object> generateDifferentObjects(final int level,
                                                  final Map<Class<?>, List<Object>> dejaVu,
                                                  final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                                                  final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Map<Class<?>, Predicate<String>> userDefinedClassAndFieldPredicatePairsMap = convertToMap(
                classAndFieldPredicatePairs);

        final Class baseClass = baseClassAndFieldPredicatePair.getClazz();
        final Predicate<String> baseClassFieldPredicate = baseClassAndFieldPredicatePair.getFieldsPredicate();

        final List<Field> baseClassFieldsToChange = FieldUtils.getFields(baseClass, baseClassFieldPredicate);
        userDefinedClassAndFieldPredicatePairsMap.put(baseClass, baseClassFieldPredicate);

        final Map<Class<?>, List<Field>> userDefinedClassAndFieldToChangePairsMap = convertToClassAndFieldsToChange(
                userDefinedClassAndFieldPredicatePairsMap);

        final List<List<Field>> baseObjectFieldsPermutations = FieldUtils.permutations(baseClassFieldsToChange);

        final Object baseObject = createNewInstance(baseClass);
        final LinkedList<Object> result = new LinkedList<>();
        result.add(baseObject);
        logWithLevel(level,
                     "Start of generating different objects for base class {}. " +
                     "Base object is {} -- others will be cloned from this one",
                     baseClassAndFieldPredicatePair,
                     baseObject);

        for (final List<Field> eachBaseObjectFieldsPermutation : baseObjectFieldsPermutations) {
            final Object baseObjectCopy = generateSameInstance(baseObject);

            final Map<Field, List<Object>> nestedObjectsThatAreWaitingForSetInBaseObjectCopy = new HashMap<>();
            List<Object> partialResult = new ArrayList<>();

            for (final Field permutationField : eachBaseObjectFieldsPermutation) {
                final Class<?> permutationFieldType = permutationField.getType();

                final List<Field> nestedFieldsToChangeInFieldType = userDefinedClassAndFieldToChangePairsMap.get(
                        permutationFieldType);

                if (nestedFieldsToChangeInFieldType == null || permutationFieldType.equals(baseClass)) {
                    Object newFieldTypeInstance = createNewInstance(permutationFieldType);
                    if (Objects.deepEquals(newFieldTypeInstance,
                                           FieldUtils.getValue(baseObjectCopy, permutationField))) {
                        newFieldTypeInstance = abstractFieldValueChanger.increaseValue(newFieldTypeInstance);
                    }

                    FieldUtils.setValue(baseObjectCopy, permutationField, newFieldTypeInstance);
                } else {
                    final List<Object> nestedObjectsOfFieldType;
                    if (dejaVu.containsKey(permutationFieldType)) {
                        nestedObjectsOfFieldType = new ArrayList<>(dejaVu.get(permutationFieldType));
                        logWithLevel(level,
                                     "Reusing {} objects from 'dejaVu' cache for {}",
                                     nestedObjectsOfFieldType.size(),
                                     permutationFieldType);
                    } else {
                        final Predicate<String> fieldPredicate = userDefinedClassAndFieldPredicatePairsMap.get(
                                permutationFieldType);
                        final List<Field> fieldClassFields = FieldUtils.getFields(permutationFieldType, fieldPredicate);

                        if (hasNestedFieldsToChange(fieldClassFields, userDefinedClassAndFieldPredicatePairsMap)) {
                            final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(
                                    permutationFieldType,
                                    fieldPredicate);
                            nestedObjectsOfFieldType = generateDifferentObjects(level + 1,
                                                                                dejaVu,
                                                                                classAndFieldPredicatePair,
                                                                                classAndFieldPredicatePairs);
                        } else {
                            nestedObjectsOfFieldType = generateDifferentObjects(permutationFieldType, fieldClassFields);
                        }
                        dejaVu.computeIfAbsent(permutationFieldType,
                                               clazz -> logAndPut(level, clazz, nestedObjectsOfFieldType));
                    }
                    nestedObjectsThatAreWaitingForSetInBaseObjectCopy.put(permutationField, nestedObjectsOfFieldType);
                }
            }

            partialResult.add(baseObjectCopy);
            for (final Map.Entry<Field, List<Object>> nestedObjectsToSet : nestedObjectsThatAreWaitingForSetInBaseObjectCopy
                    .entrySet()) {
                partialResult = createCopiesAndFillThem(partialResult, nestedObjectsToSet);
            }
            result.addAll(partialResult);
        }
        logWithLevel(level,
                     "End of generating different objects (size={}) for base class {}",
                     result.size(),
                     baseClassAndFieldPredicatePair);
        return result;
    }

    private List<Object> logAndPut(final int level, final Class<?> clazz, final List<Object> nestedObjectsOfFieldType) {
        logWithLevel(level,
                     "Caching {} different objects for {} in dejaVu cache",
                     nestedObjectsOfFieldType.size(),
                     clazz);
        return nestedObjectsOfFieldType;
    }

    private List<Object> generateDifferentObjects(final Class<?> clazz, final List<Field> fieldsToChange) {
        final List<Object> differentObjects;
        final List<List<Field>> permutationOfFields = FieldUtils.permutations(fieldsToChange);
        final Object fieldObject = createNewInstance(clazz);

        differentObjects = permutationOfFields.stream()
                                              .map(fields -> generateInstanceWithDifferentFieldValues(fieldObject,
                                                                                                      fields))
                                              .collect(Collectors.toList());
        differentObjects.add(0, fieldObject);
        return differentObjects;
    }

    private Object generateInstanceWithDifferentFieldValues(final Object baseObject, final List<Field> fieldsToChange) {
        final Object objectToChange = generateSameInstance(baseObject);
        abstractFieldValueChanger.changeFieldsValues(baseObject, objectToChange, fieldsToChange);

        return objectToChange;
    }

    private void logWithLevel(final int level, final String message, final Object... args) {
        String prefix = "";
        for (int i = 0; i < level; i++) {
            prefix += "\t";
        }
        LOGGER.debug(prefix + message, args);
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

    private boolean hasNestedFieldsToChange(final List<Field> fields, final Map<Class<?>, Predicate<String>> classes) {
        return fields.parallelStream()
                     .map(Field::getType)
                     .map(classes::get)
                     .anyMatch(Objects::nonNull);
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
                                                                      entry -> FieldUtils.getFields(entry.getKey(),
                                                                                                    entry.getValue())));
    }

    private Map<Class<?>, Predicate<String>> convertToMap(final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs) {
        return Stream.of(classAndFieldPredicatePairs)
                     .collect(Collectors.toMap(ClassAndFieldPredicatePair::getClazz,
                                               ClassAndFieldPredicatePair::getFieldsPredicate));
    }

    private Object makeThemEqual(final Object object, final Object newInstance) {
        final List<Field> allFields = getAllFields(object);
        for (final Field field : allFields) {
            final Object value = FieldUtils.getValue(object, field);
            FieldUtils.setValue(newInstance, field, value);
        }
        return newInstance;
    }

    private List<Field> getAllFields(final Object object) {
        Class<?> parent = object.getClass();
        final List<Field> allFields = new ArrayList<>();
        do {
            allFields.addAll(FieldUtils.getAllFields(parent));
        } while ((parent = parent.getSuperclass()) != null);
        return allFields;
    }

}
