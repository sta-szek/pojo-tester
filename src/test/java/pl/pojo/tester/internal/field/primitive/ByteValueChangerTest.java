package pl.pojo.tester.internal.field.primitive;


import classesForTest.fields.AllFiledTypes;
import classesForTest.fields.AllFiledTypes_Wrapped;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.powermock.reflect.Whitebox.getInternalState;


class ByteValueChangerTest {

    private final AbstractFieldValueChanger<Byte> valueChanger = new ByteValueChanger();

    @TestFactory
    Stream<DynamicTest> Should_Change_Primitive_Value() {
        return Stream.of(Byte.MAX_VALUE, Byte.MIN_VALUE, (byte) 0, (byte) -1, (byte) 1)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Primitive_Value(value)));
    }

    @TestFactory
    Stream<DynamicTest> Should_Change_Wrapped_Value() {
        return Stream.of(Byte.MAX_VALUE, Byte.MIN_VALUE, (byte) 0, (byte) -1, (byte) 1)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Wrapped_Value(value)));
    }

    @TestFactory
    Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        return Stream.of(new TestCase(null, null, false),
                         new TestCase((byte) 0, (byte) 0, false),
                         new TestCase(Byte.MIN_VALUE, Byte.MIN_VALUE, false),
                         new TestCase(Byte.MAX_VALUE, Byte.MAX_VALUE, false),
                         new TestCase((byte) 0, (byte) 1, true),
                         new TestCase((byte) 0, null, true),
                         new TestCase(null, Byte.MIN_VALUE, true),
                         new TestCase(Byte.MIN_VALUE, Byte.MAX_VALUE, true),
                         new TestCase(Byte.MAX_VALUE, Byte.MIN_VALUE, true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    private Executable Should_Change_Primitive_Value(final Byte value) {
        return () -> {
            // given
            final AllFiledTypes helpClass1 = new AllFiledTypes(value);
            final AllFiledTypes helpClass2 = new AllFiledTypes(value);

            // when
            valueChanger.changeFieldsValues(helpClass1,
                                            helpClass2,
                                            Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
            final Byte result1 = getInternalState(helpClass1, "byteType");
            final Byte result2 = getInternalState(helpClass2, "byteType");

            // then
            assertThat(result1).isNotEqualTo(result2);
        };
    }

    private Executable Should_Change_Wrapped_Value(final Byte value) {
        return () -> {
            // given
            final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
            final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

            // when
            valueChanger.changeFieldsValues(helpClass1,
                                            helpClass2,
                                            Lists.newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
            final Byte result1 = getInternalState(helpClass1, "byteType");
            final Byte result2 = getInternalState(helpClass2, "byteType");

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
        private Byte value1;
        private Byte value2;
        private boolean result;
    }

}
