package org.pojo.tester;

import java.util.List;
import java.util.function.Consumer;
import org.pojo.tester.field.AbstractFieldValueChanger;


public class EqualsTester extends AbstractTester {

    public EqualsTester() {
        super();
    }

    public EqualsTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    protected void test(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final Class<?> testedClass = classAndFieldPredicatePair.getClazz();
        final Object instance = objectGenerator.createNewInstance(testedClass);

        shouldEqualSameInstance(instance);
        shouldEqualSameInstanceFewTimes(instance);
        shouldEqualDifferentInstance(instance);
        shouldEqualObjectCifObjectBisEqualToObjectAndC(instance);
        shouldNotEqualNull(instance);
        shouldNotEqualDifferentType(instance);
        shouldNotEqualWithGivenFields(classAndFieldPredicatePair);
    }

    private void shouldEqualSameInstance(final Object object) {
        assertions.assertThatEqualsMethodFor(object)
                  .isReflexive();
    }

    private void shouldEqualSameInstanceFewTimes(final Object object) {
        assertions.assertThatEqualsMethodFor(object)
                  .isConsistent();
    }

    private void shouldEqualDifferentInstance(final Object object) {
        final Object otherObject = objectGenerator.generateSameInstance(object);
        assertions.assertThatEqualsMethodFor(object)
                  .isSymmetric(otherObject);
    }

    private void shouldEqualObjectCifObjectBisEqualToObjectAndC(final Object object) {
        final Object b = objectGenerator.generateSameInstance(object);
        final Object c = objectGenerator.generateSameInstance(object);
        assertions.assertThatEqualsMethodFor(object)
                  .isTransitive(b, c);
    }

    private void shouldNotEqualNull(final Object object) {
        assertions.assertThatEqualsMethodFor(object)
                  .isNotEqualToNull();
    }

    private void shouldNotEqualDifferentType(final Object object) {
        final Object objectToCompare = this;
        assertions.assertThatEqualsMethodFor(object)
                  .isNotEqualToObjectWithDifferentType(objectToCompare);
    }

    private void shouldNotEqualWithGivenFields(final ClassAndFieldPredicatePair classAndFieldPredicatePair) {
        final List<Object> differentObjects = objectGenerator.generateDifferentObjects(classAndFieldPredicatePair);
        final Object firstObject = differentObjects.remove(0);
        differentObjects.forEach(assertIsNotEqualTo(firstObject));
    }

    private Consumer<Object> assertIsNotEqualTo(final Object object) {
        return eachDifferentObject -> assertions.assertThatEqualsMethodFor(object)
                                                .isNotEqualTo(eachDifferentObject);
    }
//-----------------------------------------



    /*private List<Object> createObjectToAssertions(final Object baseObject, final List<Field> specifiedFields) {
        // permutacje pól do zmiany (1)
        final List<List<Field>> permutationFieldsListList = FieldUtils.permutations(specifiedFields);

        // wszystkie obiekty, które poddamy assercji
        final List<Object> objectsToAssertion = new ArrayList<>();

        // dla każdej listy pól z powstałych permutacji (2)
        for (final List<Field> permutationFieldList : permutationFieldsListList) {
            // stwórz instancję danej klasy (3)
            final Object object = objectGenerator.generateInstanceWithDifferentFieldValues(baseObject, permutationFieldList);

            final List<List<Object>> zagniezdzoneObiektyDoPermutacji = new ArrayList<>();

            // dla każdego pola z listy pól (na podstawie których został stworzony nowy obiekt) (4)
            for (final Field permutationField : permutationFieldList) {
                // pobierz z listy zdefiniowanych (przez użytkownika) klas i pól do zmiany
                final List<Field> nestedFieldsToChange = userDefiniedClassesAndFieldPredicatesToChange.get(permutationField.getType());

                // stworz egzemplasz
                final Object newNestedInstance = objectGenerator.createNewInstance(permutationField.getType());

                // jeżeli użytkownik zdefiniował taką klasę (5)
                if (nestedFieldsToChange != null) {
                    // sprawdz czy jest w dejavu (6)
                    final List<Object> childs;
                    if (dejavu.contains(permutationField.getType())) {
                        // pobierz z dejavu wczesniej utworzone obiekty (7)
                        childs = getFromDejavu();
                    } else {
                        // w przeciwnym wypadku
                        // oblicz różne obiekty dla tej klasy, na podstawie zdefiniowanych pól przez użytkownika
                        // za pomocą tej metody rekurencyjnie (8)
                        childs = createObjectToAssertions(newNestedInstance, nestedFieldsToChange);

                        // dodaj do dejavu (9)
                        dejavu.put(permutationField.getType(), childs);
                    }

                    zagniezdzoneObiektyDoPermutacji.add(childs);
                } else {
                    zagniezdzoneObiektyDoPermutacji.add(newNestedInstance);
                }

            }

            // mając listę list, z których głowna lista zawiera listy obiektów, które trzeba "powsadzać" do obiektu głownego
            // natomiast listy podrzędne to listy obiektów tej samej klasy, jednak różnią się polami, które zdefiniował użytkownik


            // stwórz permutacje z list tak że:
            // Lista< Lista<a,b>, Lista<c,d>> zwróci nam taki wynik:
            // wynikPermutacji = Lista<Lista<a,c>, Lista<a,d>, Lista<b,c>, Lista<b,d>>
            // Opisane permutacje wynikowe wpiszemy do zmiennej wynikPermutacji

            final List<List<Object>> wynikPermutacji = null;

            final List<Object> baseObjectClones = baseObject.clone(wynikPermutacji.size());

            // dla każdego sklonowanego obiektu baseObject
            for (final Object sourceObject : baseObjectClones) {
                // podmień na obiekt z kolekcji
                sourceObject.replace(permutationField, childs.next());
                // dodaj do listy wszystkich obiektów do assercji
                objectsToAssertion.add(sourceObject);
            }
            // TODO powyższa pętla jest dla przypadku, kiedy w permutationFieldList jest tylko jeden obiekt, zdefiniowany przez użytkownika
            // w przypadku, kiedy na permutacji znajduje się więcej klas to trzeba to zrobić w następujący sposób:
            // zmodyfikować tak, aby ta pętla zwracała listę zdefiniowanych przez użytkownika par (klasa, pola do zmiany)
            // które zawarte są na aktualnej liście zpermutowanych pól.
            // następnie z listy tych par, stworzyć rekurencyjnie listy list obiektów do podmiany (childs)
            // TRZEBA GDZIEŚ UWZGLĘDNIĆ LISTĘ DEJAVU
            // z listy list stworzyć n kopii obiektu sourceObject, gdzie n = l1.size() * l2.size() * ... * ln.size()
            // do tak utworzonych kopii wpisać obiekty z listy list, tak aby każdy obiekt był inny
            // zwrócić je kopie jako objectsToAssertion

        }
        return objectsToAssertion;
    }*/

}
