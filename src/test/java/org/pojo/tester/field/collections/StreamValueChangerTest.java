package org.pojo.tester.field.collections;


import java.lang.reflect.Field;
import java.util.stream.Stream;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import test.A;
import test.fields.ClassContainingStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitParamsRunner.class)
public class StreamValueChangerTest {

    private final StreamValueChanger streamValueChanger = new StreamValueChanger();

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Stream_Value(final String fieldName) throws NoSuchFieldException {
        // given
        final ClassContainingStream helpClass1 = new ClassContainingStream();
        final ClassContainingStream helpClass2 = new ClassContainingStream();

        // when
        streamValueChanger.changeFieldsValues(helpClass1,
                                              helpClass2,
                                              newArrayList(ClassContainingStream.class.getDeclaredField(fieldName)));
        final Stream result1 = getInternalState(helpClass1, fieldName);
        final Stream result2 = getInternalState(helpClass2, fieldName);

        // then
        assertThat(result2).isNotEqualTo(result1);
    }

    @Test
    @Parameters(method = "getValuesForCanChange")
    public void Should_Return_True_Or_False_Whether_Can_Change_Or_Not(final Field field, final boolean expectedResult) {
        // given

        // when
        final boolean result = streamValueChanger.canChange(field);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    @Parameters(method = "getValuesForAreDifferent")
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final Stream<?> value1,
                                                                                final Stream<?> value2,
                                                                                final boolean expectedResult) {
        // given

        // when
        final boolean result = streamValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Object[][] getValuesForAreDifferent() {
        return new Object[][]{
                {null, null, false},
                {Stream.of(1), Stream.of(1), false},
                {Stream.of(new A()), Stream.of(new A()), false},
                {Stream.empty(), Stream.empty(), false},
                {Stream.empty(), null, true},
                {null, Stream.empty(), true},
                {Stream.of(new A()), null, true},
                {Stream.of(new A()), Stream.of(1), true},
                {Stream.of(new A()), Stream.empty(), true},
                };
    }

    private Object[][] getValuesForCanChange() throws NoSuchFieldException {
        final Field field1 = ClassContainingStream.class.getDeclaredField("stream_String");
        final Field field2 = ClassContainingStream.class.getDeclaredField("stream_Object");
        final Field field3 = ClassContainingStream.class.getDeclaredField("stream_Integer");
        final Field field4 = ClassContainingStream.class.getDeclaredField("stream_A");
        final Field field5 = ClassContainingStream.class.getDeclaredField("stream");
        final Field field6 = ClassContainingStream.class.getDeclaredField("a");

        return new Object[][]{
                {field1, true},
                {field2, true},
                {field3, true},
                {field4, true},
                {field5, true},
                {field6, false},
                };
    }

    private Object[] getValuesForChangeValue() {
        return new Object[]{
                "stream_String",
                "stream_Object",
                "stream_Integer",
                "stream_A",
                "stream",
                };
    }
}
