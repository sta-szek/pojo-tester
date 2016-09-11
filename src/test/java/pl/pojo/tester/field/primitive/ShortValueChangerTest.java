package pl.pojo.tester.field.primitive;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.field.AbstractFieldValueChanger;
import test.fields.AllFiledTypes;
import test.fields.AllFiledTypes_Wrapped;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.powermock.reflect.Whitebox.getInternalState;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class ShortValueChangerTest {

    private final AbstractFieldValueChanger<Short> valueChanger = new ShortValueChanger();

    @TestFactory
    public Stream<DynamicTest> Should_Change_Primitive_Value() {
        return Stream.of(Short.MAX_VALUE, Short.MIN_VALUE, (short) 0, (short) -1, (short) 1)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Primitive_Value(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Change_Wrapped_Value() {
        return Stream.of(Short.MAX_VALUE, Short.MIN_VALUE, (short) 0, (short) -1, (short) 1)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Wrapped_Value(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        return Stream.of(new TestCase(null, null, false),
                         new TestCase((short) 0, (short) 0, false),
                         new TestCase(Short.MIN_VALUE, Short.MIN_VALUE, false),
                         new TestCase(Short.MAX_VALUE, Short.MAX_VALUE, false),
                         new TestCase((short) 0, (short) 1, true),
                         new TestCase((short) 0, null, true),
                         new TestCase(null, Short.MIN_VALUE, true),
                         new TestCase(Short.MIN_VALUE, Short.MAX_VALUE, true),
                         new TestCase(Short.MAX_VALUE, Short.MIN_VALUE, true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    private Executable Should_Change_Primitive_Value(final Short value) {
        return () -> {
            // given
            final AllFiledTypes helpClass1 = new AllFiledTypes(value);
            final AllFiledTypes helpClass2 = new AllFiledTypes(value);

            // when
            valueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
            final Short result1 = getInternalState(helpClass1, "shortType");
            final Short result2 = getInternalState(helpClass2, "shortType");

            // then
            assertThat(result1).isNotEqualTo(result2);
        };
    }

    private Executable Should_Change_Wrapped_Value(final Short value) {
        return () -> {
            // given
            final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
            final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

            // when
            valueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
            final Short result1 = getInternalState(helpClass1, "shortType");
            final Short result2 = getInternalState(helpClass2, "shortType");

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
        private Short value1;
        private Short value2;
        private boolean result;
    }
}
