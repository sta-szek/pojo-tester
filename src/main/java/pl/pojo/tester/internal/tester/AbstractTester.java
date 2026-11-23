package pl.pojo.tester.internal.tester;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.api.ConstructorParameters;
import pl.pojo.tester.api.FieldPredicate;
import pl.pojo.tester.internal.assertion.TestAssertions;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;
import pl.pojo.tester.internal.instantiator.ObjectGenerator;
import pl.pojo.tester.internal.utils.Permutator;
import pl.pojo.tester.internal.utils.ThoroughFieldPermutator;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;


public abstract class AbstractTester {

    final TestAssertions testAssertions = new TestAssertions();
    ObjectGenerator objectGenerator;
    private MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters = new ArrayListValuedHashMap<>();
    private AbstractFieldValueChanger fieldValuesChanger = DefaultFieldValueChanger.INSTANCE;
    private Permutator permutator = new ThoroughFieldPermutator();
    private Map<Class<?>, Supplier<?>> instantiators = new LinkedHashMap<>();
    private boolean changeFinalFields;

    public AbstractTester() {
        this(DefaultFieldValueChanger.INSTANCE);
    }

    public AbstractTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        this.fieldValuesChanger = abstractFieldValueChanger;
        objectGenerator = recreateObjectGenerator();
    }

    public void test(final Class<?> clazz) {
        final Predicate<String> predicateAcceptingAllFields = FieldPredicate.includeAllFields(clazz);
        test(clazz, predicateAcceptingAllFields);
    }

    public void test(final Class<?> clazz, final Predicate<String> fieldPredicate) {
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(clazz,
                                                                                                     fieldPredicate);
        test(classAndFieldPredicatePair);
    }

    public abstract void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair... classAndFieldPredicatePairs);

    public void testAll(final Class... classes) {
        final ClassAndFieldPredicatePair[] classesAndFieldPredicatesPairs = Arrays.stream(classes)
                                                                                  .map(ClassAndFieldPredicatePair::new)
                                                                                  .toArray(ClassAndFieldPredicatePair[]::new);
        testAll(classesAndFieldPredicatesPairs);

    }

    public void testAll(final ClassAndFieldPredicatePair... classesAndFieldPredicatesPairs) {
        final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs = Arrays.asList(
                classesAndFieldPredicatesPairs);
        classAndFieldPredicatePairs.forEach(base -> test(base, classesAndFieldPredicatesPairs));
    }

    public void setFieldValuesChanger(final AbstractFieldValueChanger fieldValuesChanger) {
        this.fieldValuesChanger = fieldValuesChanger;
        objectGenerator = recreateObjectGenerator();
    }

    public void setUserDefinedConstructors(final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters) {
        this.constructorParameters = constructorParameters;
        objectGenerator = recreateObjectGenerator();
    }

    public void setUserDefinedInstantiators(final Map<Class<?>, Supplier<?>> instantiators) {
        this.instantiators = instantiators == null ? new LinkedHashMap<>() : instantiators;
        objectGenerator = recreateObjectGenerator();
    }

    public void setChangeFinalFields(final boolean changeFinalFields) {
        this.changeFinalFields = changeFinalFields;
        objectGenerator = recreateObjectGenerator();
    }

    public void setPermutator(final Permutator permutator) {
        this.permutator = permutator;
        objectGenerator = recreateObjectGenerator();
    }

    private ObjectGenerator recreateObjectGenerator() {
        return new ObjectGenerator(fieldValuesChanger, constructorParameters, permutator, instantiators, changeFinalFields);
    }

    Object valueUnlike(final Object seed, final List<Object> blocked) {
        Object candidate = seed;
        for (int attempt = 0; attempt < 32; attempt++) {
            if (!isBlocked(candidate, blocked)) {
                return candidate;
            }
            final Object next = nextDistinctValue(candidate);
            if (next == null || Objects.deepEquals(next, candidate)) {
                return candidate;
            }
            candidate = next;
        }
        return candidate;
    }

    private Object nextDistinctValue(final Object candidate) {
        if (candidate == null) {
            return null;
        }
        try {
            return fieldValuesChanger.increaseValue(candidate);
        } catch (final ClassCastException incompatibleChanger) {
            return null;
        }
    }

    private boolean isBlocked(final Object candidate, final List<Object> blocked) {
        for (final Object existing : blocked) {
            if (Objects.deepEquals(candidate, existing)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(final Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }

        final AbstractTester that = (AbstractTester) otherObject;

        return new EqualsBuilder().append(objectGenerator, that.objectGenerator)
                                  .append(testAssertions, that.testAssertions)
                                  .append(constructorParameters, that.constructorParameters)
                                  .append(fieldValuesChanger, that.fieldValuesChanger)
                                  .append(changeFinalFields, that.changeFinalFields)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(objectGenerator)
                                    .append(testAssertions)
                                    .append(constructorParameters)
                                    .append(fieldValuesChanger)
                                    .append(changeFinalFields)
                                    .toHashCode();
    }

    protected MultiValuedMap<Class<?>, ConstructorParameters> getConstructorParameters() {
        return constructorParameters;
    }

    @Override
    public String toString() {
        return this.getClass()
                   .getSimpleName();
    }
}
