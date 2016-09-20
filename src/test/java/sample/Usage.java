package sample;


import classesForTest.equals.BadPojoEquals_NotConsistent;
import java.util.function.Predicate;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.api.EqualsTester;
import pl.pojo.tester.api.FieldPredicate;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;
import static pl.pojo.tester.api.assertion.Method.*;

public class Usage {

    public static void main(final String[] args) {
        final EqualsTester equalsTester = new EqualsTester();
        equalsTester.testAll(BadPojoEquals_NotConsistent.class);

        // ------
        final AbstractFieldValueChanger fieldValueChanger = DefaultFieldValueChanger.INSTANCE;

        final Class<String> stringClass = String.class;
        final Predicate<String> fieldPredicate = FieldPredicate.includeAllFields(stringClass);
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(stringClass);

        assertPojoMethodsFor(stringClass).using(fieldValueChanger)
                                         .testing(EQUALS)
                                         .testing(HASH_CODE)
                                         .testing(GETTER)
                                         .testing(SETTER)
                                         .testing(TO_STRING)
                                         .create(Sample.class, new Object[]{3, 2, 1}, new Class[]{int.class, int.class, int.class})
                                         .create(Sample.class, new Object[]{3, 2}, new Class[]{int.class, int.class})
                                         .areWellImplemented();


        assertPojoMethodsFor(stringClass, fieldPredicate).using(fieldValueChanger)
                                                         .testing(EQUALS, HASH_CODE, GETTER, SETTER, TO_STRING)
                                                         .areWellImplemented();

        assertPojoMethodsFor(classAndFieldPredicatePair).using(fieldValueChanger)
                                                        .testing(EQUALS, HASH_CODE, GETTER, SETTER, TO_STRING)
                                                        .areWellImplemented();

        assertPojoMethodsFor(classAndFieldPredicatePair, classAndFieldPredicatePair).using(fieldValueChanger)
                                                                                    .testing(EQUALS, HASH_CODE, GETTER, SETTER, TO_STRING)
                                                                                    .areWellImplemented();

        assertPojoMethodsForAll(stringClass, stringClass).using(fieldValueChanger)
                                                         .testing(EQUALS, HASH_CODE, GETTER, SETTER, TO_STRING)
                                                         .areWellImplemented();

        assertPojoMethodsForAll(classAndFieldPredicatePair, classAndFieldPredicatePair).using(fieldValueChanger)
                                                                                       .testing(EQUALS, HASH_CODE, GETTER, SETTER, TO_STRING)
                                                                                       .areWellImplemented();
    }


}
