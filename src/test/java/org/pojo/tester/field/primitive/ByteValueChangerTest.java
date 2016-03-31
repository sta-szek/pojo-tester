package org.pojo.tester.field.primitive;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.FieldsValuesChanger;
import test.utils.TestHelper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitParamsRunner.class)
public class ByteValueChangerTest {

    private final FieldsValuesChanger<Byte> byteValueChanger = new ByteValueChanger();

    @Test
    @Parameters(method = "getValuesForTest")
    public void shouldChangeValue(final Byte value) {
        // given
        final TestHelper helpClass1 = new TestHelper(value);
        final TestHelper helpClass2 = new TestHelper(value);

        // when
        byteValueChanger.changeFieldsValues(helpClass1, helpClass2);
        final Byte result = getInternalState(helpClass1, "byteType");
        final Byte result2 = getInternalState(helpClass2, "byteType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        final Byte value1 = 0;
        final Byte value2 = 0;

        // when
        final boolean result = byteValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        final Byte value1 = 0;
        final Byte value2 = 1;

        // when
        final boolean result = byteValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }

    private Object[] getValuesForTest() {
        return new Object[]{Byte.MAX_VALUE,
                            Byte.MIN_VALUE,
                            new Byte((byte) 0),
                            new Byte((byte) -1),
                            new Byte((byte) 1)};
    }

}