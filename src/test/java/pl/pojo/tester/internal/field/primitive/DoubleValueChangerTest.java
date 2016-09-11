package pl.pojo.tester.internal.field.primitive;


import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;
import test.fields.AllFiledTypes;
import test.fields.AllFiledTypes_Wrapped;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.powermock.reflect.Whitebox.getInternalState;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class DoubleValueChangerTest {

    private final AbstractFieldValueChanger<Double> valueChanger = new DoubleValueChanger();

    @TestFactory
    public Stream<DynamicTest> Should_Change_Primitive_Value() {
        return Stream.of(Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_NORMAL, (double) 0, (double) -1, (double) 1)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Primitive_Value(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Change_Wrapped_Value() {
        return Stream.of(Double.MAX_VALUE, Double.MIN_VALUE, Double.MIN_NORMAL, (double) 0, (double) -1, (double) 1)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Wrapped_Value(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        return Stream.of(new TestCase(null, null, false),
                         new TestCase((double) 0, (double) 0, false),
                         new TestCase(Double.MIN_VALUE, Double.MIN_VALUE, false),
                         new TestCase(Double.MAX_VALUE, Double.MAX_VALUE, false),
                         new TestCase((double) 0, (double) 1, true),
                         new TestCase((double) 0, null, true),
                         new TestCase(null, Double.MIN_VALUE, true),
                         new TestCase(Double.MIN_VALUE, Double.MAX_VALUE, true),
                         new TestCase(Double.MAX_VALUE, Double.MIN_VALUE, true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    private Executable Should_Change_Primitive_Value(final Double value) {
        return () -> {
            // given
            final AllFiledTypes helpClass1 = new AllFiledTypes(value);
            final AllFiledTypes helpClass2 = new AllFiledTypes(value);

            // when
            valueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
            final Double result1 = getInternalState(helpClass1, "doubleType");
            final Double result2 = getInternalState(helpClass2, "doubleType");

            // then
            assertThat(result1).isNotEqualTo(result2);
        };
    }

    private Executable Should_Change_Wrapped_Value(final Double value) {
        return () -> {
            // given
            final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
            final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

            // when
            valueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
            final Double result1 = getInternalState(helpClass1, "doubleType");
            final Double result2 = getInternalState(helpClass2, "doubleType");

            // then
            assertThat(result1).isNotEqualTo(result2);
        };
    }

    private Executable Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final TestCase value) {
        return () -> {
            // when
            final boolean result = valueChanger.areDifferentValues(value.value1, value.value2);

            // then
            assertThat(result).isEqualTo(value.result);
        };
    }

    @AllArgsConstructor
    private class TestCase {
        private Double value1;
        private Double value2;
        private boolean result;
    }

}
