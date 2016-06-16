package org.pojo.tester;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.pojo.tester.assertion.Assertions;
import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.field.DefaultFieldValueChanger;
import org.pojo.tester.instantiator.ObjectGenerator;

import java.util.Arrays;
import java.util.function.Predicate;


public abstract class Testable {

    protected final Assertions assertions = new Assertions();
    protected final ObjectGenerator objectGenerator;

    public Testable() {
        this(DefaultFieldValueChanger.INSTANCE);
    }

    public Testable(final AbstractFieldValueChanger abstractFieldValueChanger) {
        objectGenerator = new ObjectGenerator(abstractFieldValueChanger);
    }

    public void test(final Class<?> clazz, final Predicate<String> fieldPredicate) {
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(clazz, fieldPredicate);
        test(classAndFieldPredicatePair);
    }

    public void test(final Class... classes) {
        Arrays.stream(classes)
              .map(ClassAndFieldPredicatePair::new)
              .forEach(this::test);
    }

    protected abstract void test(final ClassAndFieldPredicatePair classAndFieldPredicatePair);

    @Getter
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    static class ClassAndFieldPredicatePair {
        private final Class testedClass;
        private final Predicate<String> predicate;

        ClassAndFieldPredicatePair(final Class testedClass) {
            this(testedClass, FieldPredicate.includeAllFields(testedClass));
        }
    }
}
