package pl.pojo.tester.api.assertion;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.slf4j.Logger;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.api.ConstructorParameters;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.instantiator.ClassLoader;
import pl.pojo.tester.internal.tester.AbstractTester;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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

    private final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters = new ArrayListValuedHashMap<>();
    Set<AbstractTester> testers = new HashSet<>();
    private AbstractFieldValueChanger abstractFieldValueChanger;

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

        testers.forEach(tester -> tester.setUserDefinedConstructors(constructorParameters));

        runAssertions();
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
    public AbstractAssertion create(final String qualifiedClassName, final Object[] constructorParameters, final Class<?>[] constructorParameterTypes) {
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
    public AbstractAssertion create(final String qualifiedClassName, final ConstructorParameters constructorParameters) {
        checkNotBlank("qualifiedClassName", qualifiedClassName);
        checkNotNull("constructorParameters", constructorParameters);

        final Class<?> clazz = ClassLoader.loadClass(qualifiedClassName);
        this.constructorParameters.put(clazz, constructorParameters);
        return this;
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
    public AbstractAssertion create(final Class<?> clazz, final Object[] constructorParameters, final Class<?>[] constructorParameterTypes) {
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

        this.constructorParameters.put(clazz, constructorParameters);
        return this;
    }

    protected abstract void runAssertions();

    protected void logTestersAndClasses(final Logger logger, final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        if (logger.isDebugEnabled()) {
            final String classes = Arrays.stream(classAndFieldPredicatePairs)
                                         .map(ClassAndFieldPredicatePair::toString)
                                         .collect(Collectors.joining(", ", "[", "]"));

            logger.debug("Running {} testers on {} classes", testers.size(), classAndFieldPredicatePairs.length);
            logger.debug("Testers: {}", testers);
            logger.debug("Classes: {}", classes);
        }
    }
}
