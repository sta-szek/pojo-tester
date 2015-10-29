package pojo.equals.field.primitive;

import org.junit.Test;
import org.powermock.reflect.Whitebox;
import pojo.equals.field.FieldsValuesChanger;
import pojo.equals.test.pojos.TestHelper;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class BooleanValueChangerTest {

    FieldsValuesChanger<Boolean> booleanValueChanger = new BooleanValueChanger();

    @Test
    public void shouldChangeValue() {
        // given
        TestHelper helpClass1 = new TestHelper();
        TestHelper helpClass2 = new TestHelper();

        // when
        booleanValueChanger.changeFieldsValues(helpClass1, helpClass2);
        Boolean result = Whitebox.getInternalState(helpClass1, "booleanType");
        Boolean result2 = Whitebox.getInternalState(helpClass2, "booleanType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        Boolean value1 = false;
        Boolean value2 = false;

        // when
        boolean result = booleanValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        Boolean value1 = false;
        Boolean value2 = true;

        // when
        boolean result = booleanValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }
}