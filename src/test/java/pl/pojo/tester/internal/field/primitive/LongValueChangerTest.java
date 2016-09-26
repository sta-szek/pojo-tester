package pl.pojo.tester.internal.field.primitive;

import classesForTest.fields.AllFiledTypes;
import classesForTest.fields.AllFiledTypes_Wrapped;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitPlatform.class)
public class LongValueChangerTest {

    private final AbstractFieldValueChanger<Long> valueChanger = new LongValueChanger();

    @TestFactory
    public Stream<DynamicTest> Should_Change_Primitive_Value() {
        return Stream.of(Long.MAX_VALUE, Long.MIN_VALUE, (long) 0, (long) -1, (long) 1)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Primitive_Value(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Change_Wrapped_Value() {
        return Stream.of(Long.MAX_VALUE, Long.MIN_VALUE, (long) 0, (long) -1, (long) 1)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Wrapped_Value(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        return Stream.of(new TestCase(null, null, false),
                         new TestCase((long) 0, (long) 0, false),
                         new TestCase(Long.MIN_VALUE, Long.MIN_VALUE, false),
                         new TestCase(Long.MAX_VALUE, Long.MAX_VALUE, false),
                         new TestCase((long) 0, (long) 1, true),
                         new TestCase((long) 0, null, true),
                         new TestCase(null, Long.MIN_VALUE, true),
                         new TestCase(Long.MIN_VALUE, Long.MAX_VALUE, true),
                         new TestCase(Long.MAX_VALUE, Long.MIN_VALUE, true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    private Executable Should_Change_Primitive_Value(final Long value) {
        return () -> {
            // given
            final AllFiledTypes helpClass1 = new AllFiledTypes(value);
            final AllFiledTypes helpClass2 = new AllFiledTypes(value);

            // when
            valueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
            final Long result1 = getInternalState(helpClass1, "longType");
            final Long result2 = getInternalState(helpClass2, "longType");

            // then
            assertThat(result1).isNotEqualTo(result2);
        };
    }

    private Executable Should_Change_Wrapped_Value(final Long value) {
        return () -> {
            // given
            final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
            final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

            // when
            valueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
            final Long result1 = getInternalState(helpClass1, "longType");
            final Long result2 = getInternalState(helpClass2, "longType");

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
        private Long value1;
        private Long value2;
        private boolean result;
    }
}
