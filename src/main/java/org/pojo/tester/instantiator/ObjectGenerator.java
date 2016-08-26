package org.pojo.tester.instantiator;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

    public List<Object> generateDifferentObjects(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                                                 final ClassAndFieldPredicatePair... classAndFieldPredicatePairs)
            throws IllegalAccessException {
        final Map<Class<?>, Predicate<String>> userDefinedClassAndFieldPredicatePairsMap = convertToMap(classAndFieldPredicatePairs);
        final Map<Class<?>, List<Object>> dejaVu = new HashMap<>();

        final Class baseClass = baseClassAndFieldPredicatePair.getClazz();
        final Predicate<String> baseClassFieldPredicate = baseClassAndFieldPredicatePair.getFieldsPredicate();

        final List<Field> baseClassFieldsToChange = FieldUtils.getFields(baseClass, baseClassFieldPredicate);
        userDefinedClassAndFieldPredicatePairsMap.put(baseClass, baseClassFieldPredicate);

        final Map<Class<?>, List<Field>> userDefinedClassAndFieldToChangePairsMap = convertToClassAndFieldsToChange(
                userDefinedClassAndFieldPredicatePairsMap);
        final Object baseObject = createNewInstance(baseClass);

        // permutacje pól do zmiany (1)
        final List<List<Field>> baseObjectFieldsPermutations = FieldUtils.permutations(baseClassFieldsToChange);

        // wszystkie obiekty, które poddamy assercji
        final List<Object> result = new ArrayList<>();
        result.add(baseObject);

        // dla każdej listy pól z powstałych permutacji (2)
        for (final List<Field> eachBaseObjectFieldsPermutation : baseObjectFieldsPermutations) {
            // stwórz instancję klasy bazowej (3)
            // TODO może wystarczy createNewInstance?
            final Object object = generateInstanceWithDifferentFieldValues(baseObject, eachBaseObjectFieldsPermutation);

            final Map<Field, List<Object>> nestedObjectsThatAreWaitingForSetInBaseObject = new HashMap<>();

            List<Object> partialResult = new ArrayList<>();
            // dla każdego pola z listy pól (na podstawie których został stworzony nowy obiekt) (4)
            for (final Field permutationField : eachBaseObjectFieldsPermutation) {
                // stworz egzemplarz klasy tego pola (5)
                final Object newNestedInstance = createNewInstance(permutationField.getType());

                // pobierz z listy zdefiniowanych (przez użytkownika) klas i pól do zmiany (6)
                final List<Field> nestedFieldsToChange = userDefinedClassAndFieldToChangePairsMap.get(permutationField.getType());

                // jeżeli użytkownik zdefiniował taką klasę (7)
                if (nestedFieldsToChange != null) {
                    final List<Object> childs;
                    // sprawdz czy jest w dejavu
                    if (dejaVu.containsKey(permutationField.getType())) {
                        // pobierz z dejavu wczesniej utworzone obiekty (9)
                        childs = new ArrayList<>(dejaVu.get(permutationField.getType()));
                    } else {
                        // w przeciwnym wypadku
                        // oblicz różne obiekty dla tej klasy, na podstawie zdefiniowanych pól przez użytkownika
                        // za pomocą tej metody rekurencyjnie (10)
                        final Predicate<String> fields = userDefinedClassAndFieldPredicatePairsMap.get(permutationField.getType());
                        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(permutationField
                                                                                                                             .getType(),
                                                                                                                     fields);
                        final List<Field> fieldClassFields = FieldUtils.getFields(classAndFieldPredicatePair.getClazz(),
                                                                                  classAndFieldPredicatePair.getFieldsPredicate());
                        if (hasNestedFieldsToChange(userDefinedClassAndFieldPredicatePairsMap, fieldClassFields)) {
                            childs = generateDifferentObjects(classAndFieldPredicatePair, classAndFieldPredicatePairs);
                        } else {
                            childs = generateDifferentObjects(classAndFieldPredicatePair);
                        }

                        // dodaj do dejavu (11)
                        dejaVu.putIfAbsent(permutationField.getType(), childs);
                    }
                    nestedObjectsThatAreWaitingForSetInBaseObject.put(permutationField, childs);
                } else {
                    FieldUtils.setValue(object, permutationField, newNestedInstance);
                }

            }

            partialResult.add(object);
            for (final Map.Entry<Field, List<Object>> fieldListEntry : nestedObjectsThatAreWaitingForSetInBaseObject.entrySet()) {
                partialResult = createCopiesAndFillThem(partialResult, fieldListEntry);
            }
            result.addAll(partialResult);
        }
        return result;
    }

    List<Object> createCopiesAndFillThem(final List<Object> baseObjects, final Map.Entry<Field, List<Object>> entry)
            throws IllegalAccessException {
        final List<Object> result = new ArrayList<>();

        for (final Object baseObject : baseObjects) {
            final List<Object> objectsToFillWith = entry.getValue();

            final List<Object> baseObjectClones = createCopies(baseObject, objectsToFillWith.size());

            for (int i = 0; i < baseObjectClones.size(); i++) {
                final Object baseObjectClone = baseObjectClones.get(i);
                final Object valueToSet = objectsToFillWith.get(i);
                FieldUtils.setValue(baseObjectClone, entry.getKey(), valueToSet);
            }
            result.addAll(baseObjectClones);
        }

        return result;
    }

    Object generateInstanceWithDifferentFieldValues(final Object baseObject, final List<Field> fieldsToChange) {
        final Object objectToChange = generateSameInstance(baseObject);
        abstractFieldValueChanger.changeFieldsValues(baseObject, objectToChange, fieldsToChange);

        return objectToChange;
    }

    private Boolean hasNestedFieldsToChange(final Map<Class<?>, Predicate<String>> userDefinedClassAndFieldPredicatePairsMap,
                                            final List<Field> fieldClassFields) {
        return fieldClassFields.parallelStream()
                               .map(Field::getType)
                               .map(userDefinedClassAndFieldPredicatePairsMap::get)
                               .filter(Objects::nonNull)
                               .findAny()
                               .map(anyValue -> true)
                               .orElse(false);
    }

    // TODO refactor na stream i wykorzystanie genreateSameInstance
    private List<Object> createCopies(final Object baseObject, final int size) {
        final List<Object> baseObjectClones = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            baseObjectClones.add(generateSameInstance(baseObject));
        }
        return baseObjectClones;
    }

    private Map<Class<?>, List<Field>> convertToClassAndFieldsToChange(final Map<Class<?>, Predicate<String>>
                                                                               classAndFieldPredicatePairMap) {
        return classAndFieldPredicatePairMap.entrySet()
                                            .stream()
                                            .collect(Collectors.toMap(Map.Entry::getKey,
                                                                      entry -> FieldUtils.getFields(entry.getKey(), entry.getValue())));
    }

    private Map<Class<?>, Predicate<String>> convertToMap(final ClassAndFieldPredicatePair[] classAndFieldPredicatePairs) {
        return Stream.of(classAndFieldPredicatePairs)
                     .collect(Collectors.toMap(ClassAndFieldPredicatePair::getClazz,
                                               ClassAndFieldPredicatePair::getFieldsPredicate));
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
