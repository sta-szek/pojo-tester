package pl.pojo.tester.internal.tester;

import classesForTest.ClassWithSyntheticConstructor;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.internal.assertion.constructor.ConstructorAssertionError;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;
import pl.pojo.tester.internal.field.collections.CollectionsFieldValueChanger;
import pl.pojo.tester.internal.field.date.DefaultDateAndTimeFieldValueChanger;
import pl.pojo.tester.internal.instantiator.Instantiable;
import pl.pojo.tester.internal.preconditions.ParameterPreconditions;
import pl.pojo.tester.internal.utils.ClassLoader;
import pl.pojo.tester.internal.utils.CollectionUtils;
import pl.pojo.tester.internal.utils.ReflectionUtils;
import pl.pojo.tester.internal.utils.Sublists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


class ConstructorTesterTest {

    @Test
    void Should_Pass_All_Constructor_Tests() {
        // given
        final Class[] classesToTest = { Pojo.class,
                ParameterPreconditions.class,
                CollectionsFieldValueChanger.class,
                DefaultFieldValueChanger.class,
                Assertions.class,
                Instantiable.class,
                Sublists.class,
                ReflectionUtils.class,
                DefaultDateAndTimeFieldValueChanger.class,
                CollectionUtils.class,
                ClassLoader.class };
        final ConstructorTester constructorTester = new ConstructorTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    void Should_Skip_Constructor_Tests_If_Class_Is_Abstract() {
        // given
        final Class[] classesToTest = { AbstractBadConstructorPojo.class };
        final ConstructorTester constructorTester = new ConstructorTester();

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    void Should_Fail_Constructor_Tests() {
        // given
        final Class[] classesToTest = { BadConstructorPojo.class };
        final ConstructorTester constructorTester = new ConstructorTester();

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(ConstructorAssertionError.class);
    }

    @Test
    void Should_Create_Constructor_Parameters() {
        // given
        final Class[] classesToTest = { ClassWithSyntheticConstructor.class };

        final ConstructorTester constructorTester = new ConstructorTester();

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    private static class Pojo {
        Pojo() {
        }

        Pojo(final String x) {
        }

        Pojo(final int y) {
        }
    }

    private static class BadConstructorPojo {
        BadConstructorPojo() {
            throw new RuntimeException("test");
        }
    }

    private abstract static class AbstractBadConstructorPojo {
        AbstractBadConstructorPojo() {
            throw new RuntimeException("test");
        }
    }
}