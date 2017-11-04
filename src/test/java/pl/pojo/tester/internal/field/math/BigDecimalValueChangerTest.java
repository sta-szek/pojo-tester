package pl.pojo.tester.internal.field.math;


import classesForTest.fields.AllFiledTypes;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.powermock.reflect.Whitebox.getInternalState;

class BigDecimalValueChangerTest {

    private final BigDecimalValueChanger valueChanger = new BigDecimalValueChanger();

    @TestFactory
    Stream<DynamicTest> Should_Change_Value() {
        return Stream.of(BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.valueOf(999999999999999999L))
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Value(value)));
    }

    private Executable Should_Change_Value(final BigDecimal value) {
        return () -> {
            // given
            final AllFiledTypes helpClass1 = new AllFiledTypes(value);
            final AllFiledTypes helpClass2 = new AllFiledTypes(value);

            // when
            valueChanger.changeFieldsValues(helpClass1,
                                            helpClass2,
                                            Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
            final BigDecimal result1 = getInternalState(helpClass1, "bigDecimal");
            final BigDecimal result2 = getInternalState(helpClass2, "bigDecimal");

            // then
            assertThat(result1).isNotEqualTo(result2);
        };
    }

    @TestFactory
    Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        return Stream.of(
                new TestCase(null, null, false),
                new TestCase(BigDecimal.ZERO, BigDecimal.ZERO, false),
                new TestCase(BigDecimal.valueOf(-999999999999999999L), BigDecimal.valueOf(-999999999999999999L), false),
                new TestCase(BigDecimal.valueOf(999999999999999999L), BigDecimal.valueOf(999999999999999999L), false),
                new TestCase(BigDecimal.ZERO, BigDecimal.ONE, true),
                new TestCase(BigDecimal.ZERO, null, true),
                new TestCase(null, BigDecimal.valueOf(-999999999999999999L), true),
                new TestCase(BigDecimal.valueOf(-999999999999999999L), BigDecimal.valueOf(999999999999999999L), true),
                new TestCase(BigDecimal.valueOf(999999999999999999L), BigDecimal.valueOf(-999999999999999999L), true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    private Executable Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final TestCase value) {
        return () -> {
            // when
            final boolean result = valueChanger.areDifferentValues(value.value1, value.value2);

            // then
            assertThat(result).isEqualTo(value.result);
        };
    }

    @Test
    void Should_Return_True_If_Can_Change() {
        // given
        final Class<?> classToChange = BigDecimal.class;

        // when
        final boolean result = valueChanger.canChange(classToChange);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void Should_Return_True_If_Can_Not_Change() {
        // given
        final Class<?> classToChange = String.class;

        // when
        final boolean result = valueChanger.canChange(classToChange);

        // then
        assertThat(result).isFalse();
    }

    @AllArgsConstructor
    private class TestCase {
        private BigDecimal value1;
        private BigDecimal value2;
        private boolean result;
    }
}