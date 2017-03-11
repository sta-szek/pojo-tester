package pl.pojo.tester.internal.field.collections;

import classesForTest.fields.ClassContainingArrays;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.powermock.reflect.Whitebox.getInternalState;


public class ArrayValueChangerTest {

    private final ArrayValueChanger valueChanger = new ArrayValueChanger();

    @TestFactory
    public Stream<DynamicTest> Should_Change_Array_Value() {
        return Stream.of("a_int",
                         "a_char",
                         "a_float",
                         "a_double",
                         "a_boolean",
                         "a_byte",
                         "a_short",
                         "a_long",
                         "a_Int",
                         "a_Char",
                         "a_Float",
                         "a_Double",
                         "a_Boolean",
                         "a_Byte",
                         "a_Short",
                         "a_Long",
                         "a_object_null",
                         "a_object",
                         "a_a")
                     .map(fieldName -> dynamicTest(getDefaultDisplayName(fieldName),
                                                   Should_Change_Array_Value(fieldName)));
    }

    public Executable Should_Change_Array_Value(final String fieldName) {
        return () -> {
            // given
            final ClassContainingArrays helpClass1 = new ClassContainingArrays();
            final ClassContainingArrays helpClass2 = new ClassContainingArrays();

            // when
            valueChanger.changeFieldsValues(helpClass1,
                                            helpClass2,
                                            newArrayList(ClassContainingArrays.class.getDeclaredField(fieldName)));
            final Object result1 = getInternalState(helpClass1, fieldName);
            final Object result2 = getInternalState(helpClass2, fieldName);

            // then
            assertThat(result1).isNotEqualTo(result2);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Can_Change_Or_Not() throws NoSuchFieldException {
        return Stream.of(new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_int"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_char"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_float"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_double"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_boolean"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_byte"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_short"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_long"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_Int"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_Char"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_Float"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_Double"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_Boolean"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_Byte"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_object_null"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_object"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a_a"), true),
                         new CanChangeCase(ClassContainingArrays.class.getDeclaredField("a"), false))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.field.getName()),
                                               Should_Return_True_Or_False_Whether_Can_Change_Or_Not(value)));
    }

    public Executable Should_Return_True_Or_False_Whether_Can_Change_Or_Not(final CanChangeCase testCase) {
        return () -> {
            // when
            final boolean result = valueChanger.canChange(testCase.field.getType());

            // then
            assertThat(result).isEqualTo(testCase.result);
        };
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        return Stream.of(new AreDifferentCase(null, null, false),
                         new AreDifferentCase(new int[0], new int[0], false),
                         new AreDifferentCase(new Object[1], new Object[1], false),
                         new AreDifferentCase(new Object[0], new Object[0], false),
                         new AreDifferentCase(new Object[0], null, true),
                         new AreDifferentCase(null, new Object[0], true),
                         new AreDifferentCase(new Object[1], null, true),
                         new AreDifferentCase(new Object[1], new int[0], true),
                         new AreDifferentCase(new Object[1], new Object[0], true))
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


    @AllArgsConstructor
    private class CanChangeCase {
        private Field field;
        private boolean result;
    }

    @AllArgsConstructor
    private class AreDifferentCase {

        private Object value1;
        private Object value2;
        private boolean result;
    }

}
