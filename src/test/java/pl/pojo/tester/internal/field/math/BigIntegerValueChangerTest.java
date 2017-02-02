package pl.pojo.tester.internal.field.math;


import classesForTest.fields.AllFiledTypes;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.math.BigInteger;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.powermock.reflect.Whitebox.getInternalState;

public class BigIntegerValueChangerTest {

    private final BigIntegerValueChanger valueChanger = new BigIntegerValueChanger();

    @TestFactory
    public Stream<DynamicTest> Should_Change_Value() {
        return Stream.of(BigInteger.TEN, BigInteger.ZERO, BigInteger.ONE, BigInteger.valueOf(999999999999999999L))
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Value(value)));
    }

    public Executable Should_Change_Value(final BigInteger value) {
        return () -> {
            // given
            final AllFiledTypes helpClass1 = new AllFiledTypes(value);
            final AllFiledTypes helpClass2 = new AllFiledTypes(value);

            // when
            valueChanger.changeFieldsValues(helpClass1,
                                            helpClass2,
                                            Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
            final BigInteger result1 = getInternalState(helpClass1, "bigInteger");
            final BigInteger result2 = getInternalState(helpClass2, "bigInteger");

            // then
            assertThat(result1).isNotEqualTo(result2);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        return Stream.of(
                new TestCase(null, null, false),
                new TestCase(BigInteger.ZERO, BigInteger.ZERO, false),
                new TestCase(BigInteger.valueOf(-999999999999999999L), BigInteger.valueOf(-999999999999999999L), false),
                new TestCase(BigInteger.valueOf(999999999999999999L), BigInteger.valueOf(999999999999999999L), false),
                new TestCase(BigInteger.ZERO, BigInteger.ONE, true),
                new TestCase(BigInteger.ZERO, null, true),
                new TestCase(null, BigInteger.valueOf(-999999999999999999L), true),
                new TestCase(BigInteger.valueOf(-999999999999999999L), BigInteger.valueOf(999999999999999999L), true),
                new TestCase(BigInteger.valueOf(999999999999999999L), BigInteger.valueOf(-999999999999999999L), true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    public Executable Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final TestCase value) {
        return () -> {
            // when
            final boolean result = valueChanger.areDifferentValues(value.value1, value.value2);

            // then
            assertThat(result).isEqualTo(value.result);
        };
    }

    @Test
    public void Should_Return_True_If_Can_Change() {
        // given
        final Class<?> classToChange = BigInteger.class;

        // when
        final boolean result = valueChanger.canChange(classToChange);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void Should_Return_True_If_Can_Not_Change() {
        // given
        final Class<?> classToChange = String.class;

        // when
        final boolean result = valueChanger.canChange(classToChange);

        // then
        assertThat(result).isFalse();
    }

    @AllArgsConstructor
    private class TestCase {
        private BigInteger value1;
        private BigInteger value2;
        private boolean result;
    }
}