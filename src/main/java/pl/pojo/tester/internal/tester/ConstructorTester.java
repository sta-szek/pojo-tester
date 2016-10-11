package pl.pojo.tester.internal.tester;


import pl.pojo.tester.api.ClassAndFieldPredicatePair;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructorTester extends AbstractTester {

    public ConstructorTester() {
        super();
    }

    public ConstructorTester(final AbstractFieldValueChanger abstractFieldValueChanger) {
        super(abstractFieldValueChanger);
    }

    @Override
    public void test(final ClassAndFieldPredicatePair baseClassAndFieldPredicatePair,
                     final ClassAndFieldPredicatePair... classAndFieldPredicatePairs) {
        final Class<?> testedClass = baseClassAndFieldPredicatePair.getClazz();
        final List<Constructor<?>> declaredConstructors = getUserDefinedConstructors(testedClass);

        declaredConstructors.forEach(this::tryInstantiate);
    }

    private List<Constructor<?>> getUserDefinedConstructors(final Class<?> testedClass) {
        return Arrays.stream(testedClass.getDeclaredConstructors())
                     .filter(this::isNotSynthetic)
                     .collect(Collectors.toList());

    }

    private boolean isNotSynthetic(final Constructor<?> constructor) {
        return !constructor.isSynthetic();
    }


    private void tryInstantiate(final Constructor<?> constructor) {
        final Object[] constructorParameters;
        if (userDefinedParameters(constructor)) {
            constructorParameters = getConstructorParameters().get(constructor.getDeclaringClass())
                                                              .getConstructorParameters();
        } else {
            constructorParameters = createConstructorParameters(constructor);
        }

        testAssertions.assertThatConstructor(constructor)
                      .willInstantiateClassUsing(constructorParameters);
    }

    private boolean userDefinedParameters(final Constructor<?> constructor) {
        final Class<?> declaringClass = constructor.getDeclaringClass();
        return getConstructorParameters().containsKey(declaringClass);
    }

    private Object[] createConstructorParameters(final Constructor<?> constructor) {
        return Arrays.stream(constructor.getParameterTypes())
                     .map(objectGenerator::createNewInstance)
                     .toArray();
    }


}
