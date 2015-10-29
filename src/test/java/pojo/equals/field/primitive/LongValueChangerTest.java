package pojo.equals.field.primitive;

import org.junit.Test;
import org.powermock.reflect.Whitebox;
import pojo.equals.field.FieldsValuesChanger;
import pojo.equals.test.pojos.TestHelper;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class LongValueChangerTest {

    FieldsValuesChanger<Long> longValueChanger = new LongValueChanger();

    @Test
    public void shouldChangeValue() {
        // given
        TestHelper helpClass1 = new TestHelper();
        TestHelper helpClass2 = new TestHelper();

        // when
        longValueChanger.changeFieldsValues(helpClass1, helpClass2);
        Long result = Whitebox.getInternalState(helpClass1, "longType");
        Long result2 = Whitebox.getInternalState(helpClass2, "longType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        Long value1 = 0l;
        Long value2 = 0l;

        // when
        boolean result = longValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        Long value1 = 0l;
        Long value2 = 1l;

        // when
        boolean result = longValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }
}