package org.pojo.tester.field.primitive;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.FieldsValuesChanger;
import org.powermock.reflect.Whitebox;
import test.TestHelper;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ShortValueChangerTest {

    private final FieldsValuesChanger<Short> shortValueChanger = new ShortValueChanger();

    @Test
    @Parameters(method = "getValuesForTest")
    public void shouldChangeValue(final Short value) {
        // given
        final TestHelper helpClass1 = new TestHelper(value);
        final TestHelper helpClass2 = new TestHelper(value);

        // when
        shortValueChanger.changeFieldsValues(helpClass1, helpClass2);
        final Short result = Whitebox.getInternalState(helpClass1, "shortType");
        final Short result2 = Whitebox.getInternalState(helpClass2, "shortType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        final Short value1 = 0;
        final Short value2 = 0;

        // when
        final boolean result = shortValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        final Short value1 = 0;
        final Short value2 = 1;

        // when
        final boolean result = shortValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }

    private Object[] getValuesForTest() {
        return new Object[]{Short.MAX_VALUE,
                            Short.MIN_VALUE,
                            new Short((short) 0),
                            new Short((short) -1),
                            new Short((short) 1)};
    }
}