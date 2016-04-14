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
public class LongValueChangerTest {

    private final FieldsValuesChanger<Long> longValueChanger = new LongValueChanger();

    @Test
    @Parameters(method = "getValuesForTest")
    public void shouldChangeValue(final Long value) {
        // given
        final TestHelper helpClass1 = new TestHelper(value);
        final TestHelper helpClass2 = new TestHelper(value);

        // when
        longValueChanger.changeFieldsValues(helpClass1, helpClass2);
        final Long result = Whitebox.getInternalState(helpClass1, "longType");
        final Long result2 = Whitebox.getInternalState(helpClass2, "longType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given
        final Long value1 = 0L;
        final Long value2 = 0L;

        // when
        final boolean result = longValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given
        final Long value1 = 0L;
        final Long value2 = 1L;

        // when
        final boolean result = longValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isTrue();
    }

    private Object[] getValuesForTest() {
        return new Object[]{Long.MAX_VALUE,
                            Long.MIN_VALUE,
                            0L,
                            -1L,
                            1L};
    }
}