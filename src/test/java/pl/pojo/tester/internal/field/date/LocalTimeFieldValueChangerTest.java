package pl.pojo.tester.internal.field.date;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalTime;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class LocalTimeFieldValueChangerTest {

    private final LocalTimeFieldValueChanger valueChanger = new LocalTimeFieldValueChanger();

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        final LocalTime date1 = LocalTime.now();
        final LocalTime date2 = LocalTime.now().plusHours(1);
        return Stream.of(new AreDifferentCase(null, null, false),
                         new AreDifferentCase(date1, date1, false),
                         new AreDifferentCase(null, date2, true),
                         new AreDifferentCase(date1, null, true),
                         new AreDifferentCase(date1, date2, true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    public Executable Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final AreDifferentCase testCase) {
        return () -> {
            // when
            final boolean result = valueChanger.areDifferentValues(testCase.value1, testCase.value2);

            // then
            assertThat(result).isEqualTo(testCase.result);
        };
    }

    @Test
    public void Should_Increase_Value() {
        // given
        final LocalTime beforeChange = LocalTime.now();

        // when
        final LocalTime result = valueChanger.increaseValue(beforeChange, beforeChange.getClass());

        // then
        assertThat(result).isNotEqualTo(beforeChange);
    }

    @Test
    public void Should_Return_True_If_Can_Change() {
        // given

        // when
        final boolean result = valueChanger.canChange(LocalTime.class);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void Should_Return_False_If_Can_Not_Change() {
        // given

        // when
        final boolean result = valueChanger.canChange(String.class);

        // then
        assertThat(result).isFalse();
    }

    @AllArgsConstructor
    private class AreDifferentCase {

        private LocalTime value1;
        private LocalTime value2;
        private boolean result;
    }
}
