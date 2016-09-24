package pl.pojo.tester.api.assertion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pojo.tester.api.AbstractTester;
import pl.pojo.tester.api.ConstructorParameters;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import pl.pojo.tester.internal.instantiator.ClassLoader;

import static pl.pojo.tester.internal.preconditions.ParameterPreconditions.checkNotBlank;
import static pl.pojo.tester.internal.preconditions.ParameterPreconditions.checkNotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractAssetion {

    private static final Set<AbstractTester> DEFAULT_TESTERS;

    static {
        DEFAULT_TESTERS = new HashSet<>();
        Arrays.stream(Method.values())
              .map(Method::getTester)
              .forEach(DEFAULT_TESTERS::add);
    }

    private final Map<Class<?>, ConstructorParameters> constructorParameters = new HashMap<>();
    Set<AbstractTester> testers = new HashSet<>();
    private AbstractFieldValueChanger abstractFieldValueChanger;

    public AbstractAssetion using(final AbstractFieldValueChanger abstractFieldValueChanger) {
        checkNotNull("abstractFieldValueChanger", abstractFieldValueChanger);

        this.abstractFieldValueChanger = abstractFieldValueChanger;
        return this;
    }

    public AbstractAssetion testing(final Method... methods) {
        checkNotNull("methods", methods);

        Arrays.asList(methods)
              .forEach(this::testing);
        return this;
    }

    public AbstractAssetion testing(final Method method) {
        checkNotNull("method", method);

        final AbstractTester tester = method.getTester();
        this.testers.add(tester);
        return this;
    }

    public void areWellImplemented() {
        if (testers.isEmpty()) {
            testers = DEFAULT_TESTERS;
        }
        if (abstractFieldValueChanger != null) {
            testers.forEach(tester -> tester.setFieldValuesChanger(abstractFieldValueChanger));
        }

        testers.forEach(tester -> tester.setUserDefinedConstructors(constructorParameters));

        testImplementation();
    }

    public AbstractAssetion create(final String qualifiedClassName, final Object[] constructorParameters, final Class<?>[] constructorParameterTypes) {
        checkNotBlank("qualifiedClassName", qualifiedClassName);

        final ConstructorParameters constructorParameter = new ConstructorParameters(constructorParameters, constructorParameterTypes);
        return create(qualifiedClassName, constructorParameter);
    }

    public AbstractAssetion create(final String qualifiedClassName, final ConstructorParameters constructorParameters) {
        checkNotBlank("qualifiedClassName", qualifiedClassName);
        checkNotNull("constructorParameters", constructorParameters);

        final Class<?> clazz = ClassLoader.loadClass(qualifiedClassName);
        this.constructorParameters.put(clazz, constructorParameters);
        return this;
    }

    public AbstractAssetion create(final Class<?> clazz, final Object[] constructorParameters, final Class<?>[] constructorParameterTypes) {
        checkNotNull("clazz", clazz);

        final ConstructorParameters constructorParameter = new ConstructorParameters(constructorParameters, constructorParameterTypes);
        return create(clazz, constructorParameter);
    }

    public AbstractAssetion create(final Class<?> clazz, final ConstructorParameters constructorParameters) {
        checkNotNull("clazz", clazz);
        checkNotNull("constructorParameters", constructorParameters);

        this.constructorParameters.put(clazz, constructorParameters);
        return this;
    }

    protected abstract void testImplementation();

}
