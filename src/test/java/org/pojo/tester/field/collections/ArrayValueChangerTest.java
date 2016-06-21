package org.pojo.tester.field.collections;

import java.lang.reflect.Field;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import test.fields.ClassContainingArrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitParamsRunner.class)
public class ArrayValueChangerTest {

    private final ArrayValueChanger arrayValueChanger = new ArrayValueChanger();

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Array_Value(final String fieldName) throws NoSuchFieldException {
        // given
        final ClassContainingArrays helpClass1 = new ClassContainingArrays();
        final ClassContainingArrays helpClass2 = new ClassContainingArrays();

        // when
        arrayValueChanger.changeFieldsValues(helpClass1, helpClass2, newArrayList(ClassContainingArrays.class.getDeclaredField(fieldName)));
        final Object result1 = getInternalState(helpClass1, fieldName);
        final Object result2 = getInternalState(helpClass2, fieldName);

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForCanChange")
    public void Should_Return_True_Or_False_Whether_Can_Change_Or_Not(final Field field, final boolean expectedResult) {
        // given

        // when
        final boolean result = arrayValueChanger.canChange(field);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    @Parameters(method = "getValuesForAreDifferent")
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final Object value1,
                                                                                final Object value2,
                                                                                final boolean expectedResult) {
        // given

        // when
        final boolean result = arrayValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Object[][] getValuesForAreDifferent() {
        return new Object[][]{
                {null, null, false},
                {new int[0], new int[0], false},
                {new Object[1], new Object[1], false},
                {new Object[0], new Object[0], false},
                {new Object[0], null, true},
                {null, new Object[0], true},
                {new Object[1], null, true},
                {new Object[1], new int[0], true},
                {new Object[1], new Object[0], true},
                };
    }

    private Object[][] getValuesForCanChange() throws NoSuchFieldException {
        final Field field1 = ClassContainingArrays.class.getDeclaredField("a_int");
        final Field field2 = ClassContainingArrays.class.getDeclaredField("a_char");
        final Field field3 = ClassContainingArrays.class.getDeclaredField("a_float");
        final Field field4 = ClassContainingArrays.class.getDeclaredField("a_double");
        final Field field5 = ClassContainingArrays.class.getDeclaredField("a_boolean");
        final Field field6 = ClassContainingArrays.class.getDeclaredField("a_byte");
        final Field field7 = ClassContainingArrays.class.getDeclaredField("a_short");
        final Field field8 = ClassContainingArrays.class.getDeclaredField("a_long");
        final Field field9 = ClassContainingArrays.class.getDeclaredField("a_Int");
        final Field field10 = ClassContainingArrays.class.getDeclaredField("a_Char");
        final Field field11 = ClassContainingArrays.class.getDeclaredField("a_Float");
        final Field field12 = ClassContainingArrays.class.getDeclaredField("a_Double");
        final Field field13 = ClassContainingArrays.class.getDeclaredField("a_Boolean");
        final Field field14 = ClassContainingArrays.class.getDeclaredField("a_Byte");
        final Field field15 = ClassContainingArrays.class.getDeclaredField("a_object_null");
        final Field field16 = ClassContainingArrays.class.getDeclaredField("a_object");
        final Field field17 = ClassContainingArrays.class.getDeclaredField("a_a");
        final Field field18 = ClassContainingArrays.class.getDeclaredField("a");

        return new Object[][]{
                {field1, true},
                {field2, true},
                {field3, true},
                {field4, true},
                {field5, true},
                {field6, true},
                {field7, true},
                {field8, true},
                {field9, true},
                {field10, true},
                {field11, true},
                {field12, true},
                {field13, true},
                {field14, true},
                {field15, true},
                {field16, true},
                {field17, true},
                {field18, false},
                };
    }

    private Object[] getValuesForChangeValue() {
        return new Object[]{
                "a_int",
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
                "a_a",
                };
    }
}
