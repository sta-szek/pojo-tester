package org.pojo.tester;

import java.util.Arrays;
import java.util.function.Predicate;
import org.pojo.tester.assertion.Assertions;
import org.pojo.tester.field.AbstractFieldValueChanger;
import org.pojo.tester.field.DefaultFieldValueChanger;
import org.pojo.tester.instantiator.ObjectGenerator;


public abstract class AbstractTester {

    protected final Assertions assertions = new Assertions();
    protected final ObjectGenerator objectGenerator;

    public AbstractTester() {
        this(DefaultFieldValueChanger.INSTANCE);
    }

    public AbstractTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
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


}
