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


class CharacterValueChangerTest {

    private final AbstractFieldValueChanger<Character> valueChanger = new CharacterValueChanger();

    @TestFactory
    Stream<DynamicTest> Should_Change_Primitive_Value() {
        return Stream.of(Character.MAX_VALUE,
                         Character.MIN_VALUE,
                         " ".toCharArray()[0])
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Primitive_Value(value)));
    }

    @TestFactory
    Stream<DynamicTest> Should_Change_Wrapped_Value() {
        return Stream.of(Character.MAX_VALUE,
                         Character.MIN_VALUE,
                         " ".toCharArray()[0])
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Wrapped_Value(value)));
    }

    @TestFactory
    Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        return Stream.of(new TestCase(null, null, false),
                         new TestCase((char) 0, (char) 0, false),
                         new TestCase(Character.MIN_VALUE, Character.MIN_VALUE, false),
                         new TestCase(Character.MAX_VALUE, Character.MAX_VALUE, false),
                         new TestCase((char) 0, (char) 1, true),
                         new TestCase((char) 0, null, true),
                         new TestCase(null, Character.MIN_VALUE, true),
                         new TestCase(Character.MIN_VALUE, Character.MAX_VALUE, true),
                         new TestCase(Character.MAX_VALUE, Character.MIN_VALUE, true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    private Executable Should_Change_Primitive_Value(final char value) {
        return () -> {
            // given
            final AllFiledTypes helpClass1 = new AllFiledTypes(value);
            final AllFiledTypes helpClass2 = new AllFiledTypes(value);

            // when
            valueChanger.changeFieldsValues(helpClass1,
                                            helpClass2,
                                            Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
            final Character result1 = getInternalState(helpClass1, "characterType");
            final Character result2 = getInternalState(helpClass2, "characterType");

            // then
            assertThat(result1).isNotEqualTo(result2);
        };
    }

    private Executable Should_Change_Wrapped_Value(final char value) {
        return () -> {
            // given
            final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
            final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

            // when
            valueChanger.changeFieldsValues(helpClass1,
                                            helpClass2,
                                            Lists.newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
            final Character result1 = getInternalState(helpClass1, "characterType");
            final Character result2 = getInternalState(helpClass2, "characterType");

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
        private Character value1;
        private Character value2;
        private boolean result;
    }
}
