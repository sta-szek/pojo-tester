package pl.pojo.tester.internal.tester;

import classesForTest.ClassWithSyntheticConstructor;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.ConstructorParameters;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.internal.assertion.constructor.ConstructorAssertionError;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;
import pl.pojo.tester.internal.field.collections.CollectionsFieldValueChanger;
import pl.pojo.tester.internal.instantiator.ClassLoader;
import pl.pojo.tester.internal.instantiator.Instantiable;
import pl.pojo.tester.internal.preconditions.ParameterPreconditions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;


public class ConstructorTesterTest {

    @Test
    public void Should_Pass_All_Constructor_Tests() {
        // given
        final Class[] classesToTest = {Pojo.class,
                                       ParameterPreconditions.class,
                                       CollectionsFieldValueChanger.class,
                                       DefaultFieldValueChanger.class,
                                       Assertions.class,
                                       Instantiable.class,
                                       ClassLoader.class};
        final ConstructorTester constructorTester = new ConstructorTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Skip_Constructor_Tests_If_Class_Is_Abstract() {
        // given
        final Class[] classesToTest = { AbstractBadConstructorPojo.class };
        final ConstructorTester constructorTester = new ConstructorTester();

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_Constructor_Tests() {
        // given
        final Class[] classesToTest = { BadConstructorPojo.class };
        final ConstructorTester constructorTester = new ConstructorTester();

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(ConstructorAssertionError.class);
    }

    @Test
    public void Should_Use_User_Constructor_Parameters() {
        // given
        final Class[] classesToTest = {ClassWithSyntheticConstructor.class};

        final ConstructorParameters parameters = new ConstructorParameters(new Object[]{"string"},
                                                                           new Class[]{String.class});
        final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters = spy(new ArrayListValuedHashMap<>());
        constructorParameters.put(ClassWithSyntheticConstructor.class, parameters);

        final ConstructorTester constructorTester = new ConstructorTester();
        constructorTester.setUserDefinedConstructors(constructorParameters);

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
        verify(constructorParameters).get(ClassWithSyntheticConstructor.class);
    }

    @Test
    public void Should_Create_Constructor_Parameters_When_Parameters_Are_Not_Provided() {
        // given
        final Class[] classesToTest = {ClassWithSyntheticConstructor.class};

        final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters = spy(new ArrayListValuedHashMap<>());

        final ConstructorTester constructorTester = new ConstructorTester();
        constructorTester.setUserDefinedConstructors(constructorParameters);

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
        verify(constructorParameters, never()).get(ClassWithSyntheticConstructor.class);
    }

    @Test
    public void Should_Create_Constructor_Parameters_When_Could_Not_Find_Matching_Constructor_Parameters_Types() {
        // given
        final Class[] classesToTest = {ClassWithSyntheticConstructor.class};

        final ConstructorParameters parameters = spy(new ConstructorParameters(new Object[]{"to", "many", "parameters"},
                                                                               new Class[]{String.class, String.class, String.class}));
        final MultiValuedMap<Class<?>, ConstructorParameters> constructorParameters = spy(new ArrayListValuedHashMap<>());
        constructorParameters.put(ClassWithSyntheticConstructor.class, parameters);

        final ConstructorTester constructorTester = new ConstructorTester();
        constructorTester.setUserDefinedConstructors(constructorParameters);

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
        verify(parameters, never()).getParameters();
    }

    private static class Pojo {
        public Pojo() {
        }

        public Pojo(final String x) {
        }

        public Pojo(final int y) {
        }
    }

    private static class BadConstructorPojo {
        public BadConstructorPojo() {
            throw new RuntimeException("test");
        }
    }

    private abstract static class AbstractBadConstructorPojo {
        public AbstractBadConstructorPojo() {
            throw new RuntimeException("test");
        }
    }
}