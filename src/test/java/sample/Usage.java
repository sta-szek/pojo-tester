package sample;


import java.util.function.Predicate;
import pl.pojo.tester.ClassAndFieldPredicatePair;
import pl.pojo.tester.EqualsTester;
import pl.pojo.tester.FieldPredicate;
import pl.pojo.tester.field.AbstractFieldValueChanger;
import pl.pojo.tester.field.DefaultFieldValueChanger;
import test.equals.BadPojoEquals_NotConsistent;

import static pl.pojo.tester.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.Assertions.assertPojoMethodsForAll;
import static pl.pojo.tester.Method.*;

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
                                         .testing(SETTERS_AND_GETTERS)
                                         .testing(TO_STRING)
                                         .areWellImplemented();

        assertPojoMethodsFor(stringClass, fieldPredicate).using(fieldValueChanger)
                                                         .testing(EQUALS, HASH_CODE, SETTERS_AND_GETTERS, TO_STRING)
                                                         .areWellImplemented();

        assertPojoMethodsFor(classAndFieldPredicatePair).using(fieldValueChanger)
                                                        .testing(EQUALS, HASH_CODE, SETTERS_AND_GETTERS, TO_STRING)
                                                        .areWellImplemented();

        assertPojoMethodsFor(classAndFieldPredicatePair, classAndFieldPredicatePair).using(fieldValueChanger)
                                                                                    .testing(EQUALS, HASH_CODE, SETTERS_AND_GETTERS, TO_STRING)
                                                                                    .areWellImplemented();

        assertPojoMethodsForAll(stringClass, stringClass).using(fieldValueChanger)
                                                         .testing(EQUALS, HASH_CODE, SETTERS_AND_GETTERS, TO_STRING)
                                                         .areWellImplemented();

        assertPojoMethodsForAll(classAndFieldPredicatePair, classAndFieldPredicatePair).using(fieldValueChanger)
                                                                                       .testing(EQUALS, HASH_CODE, SETTERS_AND_GETTERS, TO_STRING)
                                                                                       .areWellImplemented();
    }
}
