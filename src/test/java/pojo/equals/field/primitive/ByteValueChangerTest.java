package pojo.equals.field.primitive;


import org.junit.Test;
import org.powermock.reflect.Whitebox;
import pojo.equals.field.FieldsValuesChanger;
import pojo.equals.test.pojos.TestHelper;

import static org.assertj.core.api.Assertions.assertThat;

public class ByteValueChangerTest {

    FieldsValuesChanger<Byte> byteValueChanger = new ByteValueChanger();

    @Test
    public void shouldChangeValue() {
        // given
        TestHelper helpClass1 = new TestHelper();
        TestHelper helpClass2 = new TestHelper();

        // when
        byteValueChanger.changeFieldsValues(helpClass1, helpClass2);
        Byte result = Whitebox.getInternalState(helpClass1, "byteType");
        Byte result2 = Whitebox.getInternalState(helpClass2, "byteType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        Byte value1 = 0;
        Byte value2 = 0;

        // when
        boolean result = byteValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        Byte value1 = 0;
        Byte value2 = 1;

        // when
        boolean result = byteValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }

}