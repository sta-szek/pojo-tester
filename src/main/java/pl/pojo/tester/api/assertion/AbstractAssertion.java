package pl.pojo.tester.api.assertion;

import org.slf4j.Logger;
import pl.pojo.tester.api.AbstractObjectInstantiator;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.api.ConstructorParameters;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.instantiator.SupplierInstantiator;
import pl.pojo.tester.internal.instantiator.UserDefinedConstructorInstantiator;
import pl.pojo.tester.internal.tester.AbstractTester;
import pl.pojo.tester.internal.utils.ClassLoader;
import pl.pojo.tester.internal.utils.Permutator;
import pl.pojo.tester.internal.utils.SublistFieldPermutator;
import pl.pojo.tester.internal.utils.ThoroughFieldPermutator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static pl.pojo.tester.internal.preconditions.ParameterPreconditions.checkNotBlank;
import static pl.pojo.tester.internal.preconditions.ParameterPreconditions.checkNotNull;


/**
 * This is abstract class for all assertion classes.
 * <p>
 * For more documentation, please refer <a href="http://pojo.pl">POJO-TESTER User Guide documentation</a>
 *
 * @author Piotr Joński
 * @since 0.1.0
 */
public abstract class AbstractAssertion {

    private static final Set<AbstractTester> DEFAULT_TESTERS;

    static {
        DEFAULT_TESTERS = new HashSet<>();
        Arrays.stream(Method.values())
              .map(Method::getTester)
              .forEach(DEFAULT_TESTERS::add);
    }

    private final List<AbstractObjectInstantiator> instantiators = new LinkedList<>();
    Set<AbstractTester> testers = new HashSet<>();
    private AbstractFieldValueChanger abstractFieldValueChanger;
    private Permutator permutator = new ThoroughFieldPermutator();

    /**
     * Specifies what field values changer will be used for testing.
     *
     * @param abstractFieldValueChanger field values changer
     * @return itself
     * @see AbstractFieldValueChanger
     */
    public AbstractAssertion using(final AbstractFieldValueChanger abstractFieldValueChanger) {
        checkNotNull("abstractFieldValueChanger", abstractFieldValueChanger);

        this.abstractFieldValueChanger = abstractFieldValueChanger;
        return this;
    }

    /**
     * Specifies generation of O(2^N) test objects for N fields.
     *
     * @return itself
     */
    public AbstractAssertion thoroughly() {
        this.permutator = new ThoroughFieldPermutator();
        return this;
    }

    /**
     * Specifies generation of O(N) test objects for N fields.
     *
     * @return itself
     */
    public AbstractAssertion quickly() {
        this.permutator = new SublistFieldPermutator();
        return this;
    }

    /**
     * Specifies what tests will be performed.
     *
     * @param methods methods to test
     * @return itself
     * @see Method
     */
    public AbstractAssertion testing(final Method... methods) {
        checkNotNull("methods", methods);

        Arrays.asList(methods)
              .forEach(this::testing);
        return this;
    }

    /**
     * Specifies what test will be performed.
     *
     * @param method method to test
     * @return itself
     * @see Method
     */
    public AbstractAssertion testing(final Method method) {
        checkNotNull("method", method);

        final AbstractTester tester = method.getTester();
        this.testers.add(tester);
        return this;
    }

    /**
     * Indicates, that class should be constructed using given constructor parameters. Constructor will be selected
     * based on constructor parameter's types.
     *
     * @param qualifiedClassName        class to instantiate
     * @param constructorParameters     constructor parameters
     * @param constructorParameterTypes constructor parameter's types
     * @return itself
     * @see ConstructorParameters
     */
    public AbstractAssertion create(final String qualifiedClassName,
                                    final Object[] constructorParameters,
                                    final Class<?>[] constructorParameterTypes) {
        checkNotBlank("qualifiedClassName", qualifiedClassName);

        final ConstructorParameters constructorParameter = new ConstructorParameters(constructorParameters,
                                                                                     constructorParameterTypes);
        return create(qualifiedClassName, constructorParameter);
    }

    /**
     * Indicates, that class should be constructed using given constructor parameters. Constructor will be selected
     * based on constructor parameter's types.
     *
     * @param qualifiedClassName    class to instantiate
     * @param constructorParameters constructor parameters
     * @return itself
     * @see ConstructorParameters
     */
    public AbstractAssertion create(final String qualifiedClassName,
                                    final ConstructorParameters constructorParameters) {
        checkNotBlank("qualifiedClassName", qualifiedClassName);
        checkNotNull("constructorParameters", constructorParameters);

        final Class<?> clazz = ClassLoader.loadClass(qualifiedClassName);

        return create(clazz, constructorParameters);
    }

    /**
     * Indicates, that class should be constructed using given constructor parameters. Constructor will be selected
     * based on constructor parameter's types.
     *
     * @param clazz                     class to instantiate
     * @param constructorParameters     constructor parameters
     * @param constructorParameterTypes constructor parameter's types
     * @return itself
     * @see ConstructorParameters
     */
    public AbstractAssertion create(final Class<?> clazz,
                                    final Object[] constructorParameters,
                                    final Class<?>[] constructorParameterTypes) {
        checkNotNull("clazz", clazz);

        final ConstructorParameters constructorParameter = new ConstructorParameters(constructorParameters,
                                                                                     constructorParameterTypes);
        return create(clazz, constructorParameter);
    }

    /**
     * Indicates, that class should be constructed using given constructor parameters. Constructor will be selected
     * based on constructor parameter's types.
     *
     * @param clazz                 class to instantiate
     * @param constructorParameters constructor parameters
     * @return itself
     * @see ConstructorParameters
     */
    public AbstractAssertion create(final Class<?> clazz, final ConstructorParameters constructorParameters) {
        checkNotNull("clazz", clazz);
        checkNotNull("constructorParameters", constructorParameters);

        final UserDefinedConstructorInstantiator instantiator = new UserDefinedConstructorInstantiator(clazz,
                                                                                                       constructorParameters);
        return create(clazz, instantiator);
    }

    /**
     * Indicates, that class should be constructed using given instantiator.
     *
     * @param instantiator instantiator which will create instance of given class
     * @return itself
     * @see ConstructorParameters
     */
    public AbstractAssertion create(final AbstractObjectInstantiator instantiator) {
        checkNotNull("clazz", instantiator.getClazz());
        checkNotNull("instantiator", instantiator);

        return create(instantiator.getClazz(), instantiator::instantiate);
    }

    /**
     * Indicates, that class should be constructed using given instantiator.
     *
     * @param clazz        class to instantiate
     * @param instantiator instantiator which will create instance of given class
     * @return itself
     * @see ConstructorParameters
     */
    public AbstractAssertion create(final Class<?> clazz, final AbstractObjectInstantiator instantiator) {
        checkNotNull("clazz", clazz);
        checkNotNull("instantiator", instantiator);

        return create(clazz, instantiator::instantiate);
    }

    /**
     * Indicates, that class should be constructed using given supplier.
     *
     * @param clazz    class to instantiate
     * @param supplier supplier that will create given class
     * @return itself
     * @see ConstructorParameters
     */
    public AbstractAssertion create(final Class<?> clazz, final Supplier<?> supplier) {
        checkNotNull("clazz", clazz);
        checkNotNull("supplier", supplier);

        this.instantiators.add(new SupplierInstantiator(clazz, supplier));
        return this;
    }

    /**
     * Performs specified tests on classes using declared field value changer.
     *
     * @see Method
     * @see AbstractFieldValueChanger
     */
    public void areWellImplemented() {
        if (testers.isEmpty()) {
            testers = DEFAULT_TESTERS;
        }
        if (abstractFieldValueChanger != null) {
            testers.forEach(tester -> tester.setFieldValuesChanger(abstractFieldValueChanger));
        }

        testers.forEach(tester -> tester.setPermutator(permutator));
        testers.forEach(tester -> tester.setUserDefinedInstantiators(instantiators));

        runAssertions();
    }

    protected abstract void runAssertions();

    protected void logTestersAndClasses(final Logger logger,
                                        final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        if (logger.isDebugEnabled()) {
            final String classes = Arrays.stream(classAndFieldPredicatePairs)
                                         .map(ClassAndFieldPredicatePair::toString)
                                         .collect(Collectors.joining(", ", "[", "]"));

            logger.debug("Running {} testers on {} classes", testers.size(), classAndFieldPredicatePairs.length);
            logger.debug("Testers: {}", testers);
            logger.debug("Classes: {}", classes);
            logger.debug("Predefined instantiators: {}", instantiators);
        }
    }
}
