package pojo.equals.field.primitive;

import org.junit.Test;
import org.powermock.reflect.Whitebox;
import pojo.equals.field.FieldsValuesChanger;
import pojo.equals.test.pojos.TestHelper;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class CharacterValueChangerTest {

    FieldsValuesChanger<Character> charValueChanger = new CharacterValueChanger();

    @Test
    public void shouldChangeValue() {
        // given
        TestHelper helpClass1 = new TestHelper();
        TestHelper helpClass2 = new TestHelper();

        // when
        charValueChanger.changeFieldsValues(helpClass1, helpClass2);
        Character result = Whitebox.getInternalState(helpClass1, "characterType");
        Character result2 = Whitebox.getInternalState(helpClass2, "characterType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        Character value1 = 0;
        Character value2 = 0;

        // when
        boolean result = charValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        Character value1 = 0;
        Character value2 = 1;

        // when
        boolean result = charValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }
}