package org.pojo.tester.field.primitive;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.FieldsValuesChanger;
import test.utils.TestHelper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitParamsRunner.class)
public class FloatValueChangerTest {

    private final FieldsValuesChanger<Float> floatValueChanger = new FloatValueChanger();

    @Test
    @Parameters(method = "getValuesForTest")
    public void shouldChangeValue(final Float value) {
        // given
        final TestHelper helpClass1 = new TestHelper(value);
        final TestHelper helpClass2 = new TestHelper(value);

        // when
        floatValueChanger.changeFieldsValues(helpClass1, helpClass2);
        final Float result = getInternalState(helpClass1, "floatType");
        final Float result2 = getInternalState(helpClass2, "floatType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        final Float value1 = 0f;
        final Float value2 = 0f;

        // when
        final boolean result = floatValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        final Float value1 = 0f;
        final Float value2 = 1f;

        // when
        final boolean result = floatValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }

    private Object[] getValuesForTest() {
        return new Object[]{Float.MAX_VALUE,
                            Float.MIN_VALUE,
                            Float.MIN_NORMAL,
                            0f,
                            1f,
                            -1f};
    }
}