package pl.pojo.tester.api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.pojo.tester.internal.assertion.TestAssertions;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;
import pl.pojo.tester.internal.instantiator.ObjectGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * AbstractTester is basic class for all pojo method testers.
 * It provides basic class conversion to {@link ClassAndFieldPredicatePair} via test methods.
 *
 * @author Piotr Joński
 * @since 0.1.0
 */
public abstract class AbstractTester {

    protected ObjectGenerator objectGenerator;
    protected TestAssertions testAssertions = new TestAssertions();
    private Map<Class<?>, ConstructorParameters> constructorParameters = new HashMap<>();
    private AbstractFieldValueChanger fieldValuesChanger = DefaultFieldValueChanger.INSTANCE;

    /**
     * Instantiates tester with {@link DefaultFieldValueChanger default fields values changer}.
     *
     * @see AbstractFieldValueChanger
     */
    public AbstractTester() {
        this(DefaultFieldValueChanger.INSTANCE);
    }

    /**
     * Instantiates tester with given default fields values changer.
     *
     * @param abstractFieldValueChanger field values changer to be set for this tester
     *
     * @see AbstractFieldValueChanger
     */
    public AbstractTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        objectGenerator = new ObjectGenerator(abstractFieldValueChanger, constructorParameters);
    }

    /**
     * Tests single class without changing fields recursively.
     * By default, all fields of given class are included in tests.
     *
     * @param clazz class to test
     */
    public void test(final Class<?> clazz) {
        final Predicate<String> predicateAcceptingAllFields = FieldPredicate.includeAllFields(clazz);
        test(clazz, predicateAcceptingAllFields);
    }

    /**
     * Tests single class with given fields without changing fields recursively.
     *
     * @param clazz          class to test
     * @param fieldPredicate fields, which will be tested
     *
     * @see FieldPredicate
     */
    public void test(final Class<?> clazz, final Predicate<String> fieldPredicate) {
        final ClassAndFieldPredicatePair classAndFieldPredicatePair = new ClassAndFieldPredicatePair(clazz,
                                                                                                     fieldPredicate);
        test(classAndFieldPredicatePair);
    }

    /**
     * Tests multiple classes. Fields of classes are changed recursively if {@code classes} contains nested field class.
     * By default, all fields of given classes are included in tests.
     *
     * @param classes classes to test
     */
    public void testAll(final Class... classes) {
        final ClassAndFieldPredicatePair[] classesAndFieldPredicatesPairs = Arrays.stream(classes)
                                                                                  .map(ClassAndFieldPredicatePair::new)
                                                                                  .toArray(ClassAndFieldPredicatePair[]::new);
        testAll(classesAndFieldPredicatesPairs);

    }

    /**
     * Tests multiple classes. Fields of classes are changed recursively if {@code classesAndFieldPredicatesPairs}
     * contains nested field class.
     *
     * @param classesAndFieldPredicatesPairs class to test
     *
     * @see ClassAndFieldPredicatePair
     * @see FieldPredicate
     */
    public void testAll(final ClassAndFieldPredicatePair... classesAndFieldPredicatesPairs) {
        final List<ClassAndFieldPredicatePair> classAndFieldPredicatePairs = Arrays.asList(
                classesAndFieldPredicatesPairs);
        classAndFieldPredicatePairs.forEach(base -> test(base, classesAndFieldPredicatesPairs));
    }

    /**
     * Tests base class using specified fields. {@code classAndFieldPredicatePairs} are used for chaning nested fields
     * recursivelly, if occures.
     *
     * @param baseClassAndFieldPredicatePair base to test
     * @param classAndFieldPredicatePairs    classes used for changing nested fields recursively
     *
     * @see ClassAndFieldPredicatePair
     * @see FieldPredicate
     */
    public abstract void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                              final ClassAndFieldPredicatePair... classAndFieldPredicatePairs);

    /**
     * Sets new field values changer.
     *
     * @param fieldValuesChanger field value changer to set
     *
     * @see AbstractFieldValueChanger
     */
    public void setFieldValuesChanger(final AbstractFieldValueChanger fieldValuesChanger) {
        this.fieldValuesChanger = fieldValuesChanger;
        objectGenerator = new ObjectGenerator(fieldValuesChanger, constructorParameters);
    }

    /**
     * Sets constructors declared by user. Those constructors will be used when instantiating classes
     *
     * @param constructorParameters map of classes and constructor parameters to use
     *
     * @see ConstructorParameters
     */
    public void setUserDefinedConstructors(final Map<Class<?>, ConstructorParameters> constructorParameters) {
        this.constructorParameters = constructorParameters;
        objectGenerator = new ObjectGenerator(fieldValuesChanger, constructorParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AbstractTester that = (AbstractTester) o;

        return new EqualsBuilder().append(objectGenerator, that.objectGenerator)
                                  .append(testAssertions, that.testAssertions)
                                  .append(constructorParameters, that.constructorParameters)
                                  .append(fieldValuesChanger, that.fieldValuesChanger)
                                  .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(objectGenerator)
                                    .append(testAssertions)
                                    .append(constructorParameters)
                                    .append(fieldValuesChanger)
                                    .toHashCode();
    }

    protected Map<Class<?>, ConstructorParameters> getConstructorParameters() {
        return constructorParameters;
    }
}
