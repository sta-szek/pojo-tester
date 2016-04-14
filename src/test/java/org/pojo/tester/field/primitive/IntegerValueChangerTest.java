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
public class IntegerValueChangerTest {

    private final FieldsValuesChanger<Integer> integerValueChanger = new IntegerValueChanger();

    @Test
    @Parameters(method = "getValuesForTest")
    public void shouldChangeValue(final Integer value) {
        // given
        final TestHelper helpClass1 = new TestHelper(value);
        final TestHelper helpClass2 = new TestHelper(value);

        // when
        integerValueChanger.changeFieldsValues(helpClass1, helpClass2);
        final Integer result = getInternalState(helpClass1, "intType");
        final Integer result2 = getInternalState(helpClass2, "intType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        final Integer value1 = 0;
        final Integer value2 = 0;

        // when
        final boolean result = integerValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        final Integer value1 = 0;
        final Integer value2 = 1;

        // when
        final boolean result = integerValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }

    private Object[] getValuesForTest() {
        return new Object[]{Integer.MAX_VALUE,
                            Integer.MIN_VALUE,
                            0,
                            -1,
                            1};
    }
}