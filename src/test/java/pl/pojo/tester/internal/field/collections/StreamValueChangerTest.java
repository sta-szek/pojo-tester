package pl.pojo.tester.internal.field.collections;


import classesForTest.fields.ClassContainingStream;
import java.lang.reflect.Field;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitPlatform.class)
public class StreamValueChangerTest {

    private final StreamValueChanger valueChanger = new StreamValueChanger();

    @TestFactory
    public Stream<DynamicTest> Should_Change_Stream_Value() {
        return Stream.of("stream_String", "stream_Object", "stream_Integer", "stream_A", "stream")
                     .map(fieldName -> dynamicTest(getDefaultDisplayName(fieldName), Should_Change_Stream_Value(fieldName)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Can_Change_Or_Not() throws NoSuchFieldException {
        return Stream.of(new CanChangeCase(ClassContainingStream.class.getDeclaredField("stream_String"), true),
                         new CanChangeCase(ClassContainingStream.class.getDeclaredField("stream_Object"), true),
                         new CanChangeCase(ClassContainingStream.class.getDeclaredField("stream_Integer"), true),
                         new CanChangeCase(ClassContainingStream.class.getDeclaredField("stream_A"), true),
                         new CanChangeCase(ClassContainingStream.class.getDeclaredField("stream"), true),
                         new CanChangeCase(ClassContainingStream.class.getDeclaredField("a"), false))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.field.getName()),
                                               Should_Return_True_Or_False_Whether_Can_Change_Or_Not(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        return Stream.of(new AreDifferentCase(null, null, false),
                         new AreDifferentCase(Stream.of(1), Stream.of(1), false),
                         new AreDifferentCase(Stream.of(new Pojo()), Stream.of(new Pojo()), false),
                         new AreDifferentCase(Stream.empty(), Stream.empty(), false),
                         new AreDifferentCase(Stream.empty(), null, true),
                         new AreDifferentCase(null, Stream.empty(), true),
                         new AreDifferentCase(Stream.of(new Pojo()), null, true),
                         new AreDifferentCase(Stream.of(new Pojo()), Stream.of(1), true),
                         new AreDifferentCase(Stream.of(new Pojo()), Stream.empty(), true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    private Executable Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final AreDifferentCase testCase) {
        return () -> {
            // when
            final boolean result = valueChanger.areDifferentValues(testCase.value1, testCase.value2);

            // then
            assertThat(result).isEqualTo(testCase.result);
        };
    }

    private Executable Should_Return_True_Or_False_Whether_Can_Change_Or_Not(final CanChangeCase testCase) {
        return () -> {
            // when
            final boolean result = valueChanger.canChange(testCase.field.getType());

            // then
            assertThat(result).isEqualTo(testCase.result);
        };
    }

    private Executable Should_Change_Stream_Value(final String fieldName) {
        return () -> {
            // given
            final ClassContainingStream helpClass1 = new ClassContainingStream();
            final ClassContainingStream helpClass2 = new ClassContainingStream();

            // when
            valueChanger.changeFieldsValues(helpClass1, helpClass2, newArrayList(ClassContainingStream.class.getDeclaredField(fieldName)));
            final Stream result1 = getInternalState(helpClass2, fieldName);
            final Stream result2 = getInternalState(helpClass1, fieldName);

            // then
            assertThat(result1).isNotEqualTo(result2);
        };
    }

    @AllArgsConstructor
    private class CanChangeCase {
        private Field field;
        private boolean result;
    }

    @AllArgsConstructor
    private class AreDifferentCase {

        private Stream<?> value1;
        private Stream<?> value2;
        private boolean result;
    }

    private class Pojo {
        private int a;

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final Pojo pojo = (Pojo) o;

            return a == pojo.a;

        }

        @Override
        public int hashCode() {
            return a;
        }
    }

}
