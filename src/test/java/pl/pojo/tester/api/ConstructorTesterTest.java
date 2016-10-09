package pl.pojo.tester.api;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.internal.assertion.constructor.ConstructorAssertionError;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(JUnitPlatform.class)
public class ConstructorTesterTest {

    @Test
    public void Should_Pass_All_Constructor_Tests() {
        // given
        final Class[] classesToTest = {Pojo.class};
        final ConstructorTester constructorTester = new ConstructorTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_Constructor_Tests() {
        // given
        final Class[] classesToTest = {BadConstructorPojo.class};
        final ConstructorTester constructorTester = new ConstructorTester();

        // when
        final Throwable result = catchThrowable(() -> constructorTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(ConstructorAssertionError.class);
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
}