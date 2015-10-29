package pojo.equals.field.primitive;


import org.junit.Test;
import org.powermock.reflect.Whitebox;
import pojo.equals.field.FieldsValuesChanger;
import pojo.equals.test.pojos.TestHelper;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class DoubleValueChangerTest {

    FieldsValuesChanger<Double> doubleValueChanger = new DoubleValueChanger();

    @Test
    public void shouldChangeValue() {
        // given
        TestHelper helpClass1 = new TestHelper();
        TestHelper helpClass2 = new TestHelper();

        // when
        doubleValueChanger.changeFieldsValues(helpClass1, helpClass2);
        Double result = Whitebox.getInternalState(helpClass1, "doubleType");
        Double result2 = Whitebox.getInternalState(helpClass2, "doubleType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        Double value1 = 0d;
        Double value2 = 0d;

        // when
        boolean result = doubleValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        Double value1 = 0d;
        Double value2 = 1d;

        // when
        boolean result = doubleValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }
}