package org.pojo.tester.field.primitive;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.FieldsValuesChanger;
import test.TestHelper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitParamsRunner.class)
public class DoubleValueChangerTest {

    private final FieldsValuesChanger<Double> doubleValueChanger = new DoubleValueChanger();

    @Test
    @Parameters(method = "getValuesForTest")
    public void shouldChangeValue(final Double value) {
        // given
        final TestHelper helpClass1 = new TestHelper(value);
        final TestHelper helpClass2 = new TestHelper(value);

        // when
        doubleValueChanger.changeFieldsValues(helpClass1, helpClass2);
        final Double result = getInternalState(helpClass1, "doubleType");
        final Double result2 = getInternalState(helpClass2, "doubleType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        final Double value1 = 0d;
        final Double value2 = 0d;

        // when
        final boolean result = doubleValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        final Double value1 = 0d;
        final Double value2 = 1d;

        // when
        final boolean result = doubleValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }

    private Object[] getValuesForTest() {
        return new Object[]{Double.MAX_VALUE,
                            Double.MIN_VALUE,
                            Double.MIN_NORMAL,
                            0D,
                            1D,
                            -1D};
    }
}