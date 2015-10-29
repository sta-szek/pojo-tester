package pojo.equals.field.primitive;

import org.junit.Test;
import org.powermock.reflect.Whitebox;
import pojo.equals.field.FieldsValuesChanger;
import pojo.equals.test.pojos.TestHelper;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class ShortValueChangerTest {

    FieldsValuesChanger<Short> shortValueChanger = new ShortValueChanger();

    @Test
    public void shouldChangeValue() {
        // given
        TestHelper helpClass1 = new TestHelper();
        TestHelper helpClass2 = new TestHelper();

        // when
        shortValueChanger.changeFieldsValues(helpClass1, helpClass2);
        Short result = Whitebox.getInternalState(helpClass1, "shortType");
        Short result2 = Whitebox.getInternalState(helpClass2, "shortType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        Short value1 = 0;
        Short value2 = 0;

        // when
        boolean result = shortValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        Short value1 = 0;
        Short value2 = 1;

        // when
        boolean result = shortValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }
}