package pl.pojo.tester.internal.tester;


import lombok.extern.slf4j.Slf4j;
import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.api.ConstructorParameters;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class ConstructorTester extends AbstractTester {

    public ConstructorTester() {
        super();
    }

    public ConstructorTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair, final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Class<?> testedClass = baseClassAndFieldPredicatePair.getClazz();
        if (isAbstract(testedClass)) {
            log.info("Tried to test constructor in abstract ({}) class, annotation or interface.\n"
                             + "Skipping due to nature of constructors in those classes", testedClass);
            return;
        }
        final List<Constructor<?>> declaredConstructors = getNotSyntheticConstructorFromClass(testedClass);

        declaredConstructors.forEach(this::tryInstantiate);
    }

    private List<Constructor<?>> getNotSyntheticConstructorFromClass(final Class<?> testedClass) {
        return Arrays.stream(testedClass.getDeclaredConstructors())
                     .filter(this::isNotSynthetic)
                     .collect(Collectors.toList());
    }

    private boolean isNotSynthetic(final Constructor<?> constructor) {
        return !constructor.isSynthetic();
    }

    private void tryInstantiate(final Constructor<?> constructor) {
        final Object[] parameters;
        final Predicate<ConstructorParameters> matchingConstructorParameterTypes = ctr -> ctr.matches(constructor.getParameterTypes());

        if (constructorParametersAreProvided(constructor)) {
            final Collection<ConstructorParameters> constructorParameters = getConstructorParameters(constructor);
            parameters = constructorParameters.stream()
                                              .filter(matchingConstructorParameterTypes)
                                              .map(ConstructorParameters::getParameters)
                                              .findFirst()
                                              .orElseGet(() -> logAndTryToCreateOwnParameters(constructor));
        } else {
            parameters = createConstructorParameters(constructor);
        }

        testAssertions.assertThatConstructor(constructor)
                      .willInstantiateClassUsing(parameters);
    }

    private Object[] logAndTryToCreateOwnParameters(final Constructor<?> constructor) {
        log.warn("Class '{}' could not be created by constructor '{}' and any user defined parameters.",
                 constructor.getDeclaringClass(),
                 constructor);
        return createConstructorParameters(constructor);
    }

    private Collection<ConstructorParameters> getConstructorParameters(final Constructor<?> constructor) {
        return getConstructorParameters().get(constructor.getDeclaringClass());
    }

    private boolean constructorParametersAreProvided(final Constructor<?> constructor) {
        final Class<?> declaringClass = constructor.getDeclaringClass();
        return getConstructorParameters().containsKey(declaringClass);
    }

    private Object[] createConstructorParameters(final Constructor<?> constructor) {
        return Arrays.stream(constructor.getParameterTypes())
                     .map(objectGenerator::createNewInstance)
                     .toArray();
    }

    private boolean isAbstract(final Class<?> clazz) {
        return clazz.isInterface() || clazz.isAnnotation() || Modifier.isAbstract(clazz.getModifiers());
    }
}
