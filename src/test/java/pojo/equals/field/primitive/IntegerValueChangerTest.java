package pojo.equals.field.primitive;


import org.junit.Test;
import pojo.equals.field.FieldsValuesChanger;
import pojo.equals.test.pojos.TestHelper;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.powermock.reflect.Whitebox.getInternalState;

public class IntegerValueChangerTest {

    FieldsValuesChanger<Integer> integerValueChanger = new IntegerValueChanger();

    @Test
    public void shouldChangeValue() {
        // given
        TestHelper helpClass1 = new TestHelper();
        TestHelper helpClass2 = new TestHelper();

        // when
        integerValueChanger.changeFieldsValues(helpClass1, helpClass2);
        Integer result = getInternalState(helpClass1, "intType");
        Integer result2 = getInternalState(helpClass2, "intType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        Integer value1 = 0;
        Integer value2 = 0;

        // when
        boolean result = integerValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        Integer value1 = 0;
        Integer value2 = 1;

        // when
        boolean result = integerValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }
}