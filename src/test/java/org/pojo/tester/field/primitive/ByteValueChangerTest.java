package org.pojo.tester.field.primitive;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.AbstractFieldValueChanger;
import test.fields.AllFiledTypes;
import test.fields.AllFiledTypes_Wrapped;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitParamsRunner.class)
public class ByteValueChangerTest {

    private final AbstractFieldValueChanger<Byte> byteValueChanger = new ByteValueChanger();

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Primitive_Value(final Byte value) {
        // given
        final AllFiledTypes helpClass1 = new AllFiledTypes(value);
        final AllFiledTypes helpClass2 = new AllFiledTypes(value);

        // when
        byteValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
        final Byte result1 = getInternalState(helpClass1, "byteType");
        final Byte result2 = getInternalState(helpClass2, "byteType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Wrapped_Value(final Byte value) {
        // given
        final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
        final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

        // when
        byteValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
        final Byte result1 = getInternalState(helpClass1, "byteType");
        final Byte result2 = getInternalState(helpClass2, "byteType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForAreDifferent")
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final Byte value1,
                                                                                final Byte value2,
                                                                                final boolean expectedResult) {
        // given

        // when
        final boolean result = byteValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Object[][] getValuesForAreDifferent() {
        return new Object[][]{
                {null, null, false},
                {(byte) 0, (byte) 0, false},
                {Byte.MIN_VALUE, Byte.MIN_VALUE, false},
                {Byte.MAX_VALUE, Byte.MAX_VALUE, false},

                {(byte) 0, (byte) 1, true},
                {(byte) 0, null, true},
                {null, Byte.MIN_VALUE, true},
                {Byte.MIN_VALUE, Byte.MAX_VALUE, true},
                {Byte.MAX_VALUE, Byte.MIN_VALUE, true},

                };
    }

    private Object[] getValuesForChangeValue() {
        return new Object[]{Byte.MAX_VALUE,
                            Byte.MIN_VALUE,
                            new Byte((byte) 0),
                            new Byte((byte) -1),
                            new Byte((byte) 1)};
    }

}
