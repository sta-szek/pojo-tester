package org.pojo.tester.field.primitive;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.AbstractFieldsValuesChanger;
import test.fields.AllFiledTypes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitParamsRunner.class)
public class CharacterValueChangerTest {

    private final AbstractFieldsValuesChanger<Character> charValueChanger = new CharacterValueChanger();

    @Test
    @Parameters(method = "getValuesForTest")
    public void shouldChangeValue(final Character value) {
        // given
        final AllFiledTypes helpClass1 = new AllFiledTypes(value);
        final AllFiledTypes helpClass2 = new AllFiledTypes(value);

        // when
        charValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
        final Character result = getInternalState(helpClass1, "characterType");
        final Character result2 = getInternalState(helpClass2, "characterType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        final Character value1 = 0;
        final Character value2 = 0;

        // when
        final boolean result = charValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        final Character value1 = 0;
        final Character value2 = 1;

        // when
        final boolean result = charValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }

    private Object[] getValuesForTest() {
        return new Object[]{Character.MAX_CODE_POINT,
                            Character.MAX_HIGH_SURROGATE,
                            Character.MAX_LOW_SURROGATE,
                            Character.MAX_RADIX,
                            Character.MAX_SURROGATE,
                            Character.MAX_VALUE,
                            Character.MIN_CODE_POINT,
                            Character.MIN_HIGH_SURROGATE,
                            Character.MIN_LOW_SURROGATE,
                            Character.MIN_RADIX,
                            Character.MIN_SURROGATE,
                            Character.MIN_VALUE};
    }
}