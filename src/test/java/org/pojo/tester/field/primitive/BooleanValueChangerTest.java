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
public class BooleanValueChangerTest {

    private final FieldsValuesChanger<Boolean> booleanValueChanger = new BooleanValueChanger();

    @Test
    @Parameters(method = "getValuesForTest")
    public void shouldChangeValue(final Boolean value) {
        // given
        final TestHelper helpClass1 = new TestHelper(value);
        final TestHelper helpClass2 = new TestHelper(value);

        // when
        booleanValueChanger.changeFieldsValues(helpClass1, helpClass2);
        final Boolean result = getInternalState(helpClass1, "booleanType");
        final Boolean result2 = getInternalState(helpClass2, "booleanType");

        // then
        assertThat(result).isNotEqualTo(result2);
    }

    @Test
    public void shouldReturnFalseForSameValues() {
        // given

        // when
        final boolean result = booleanValueChanger.areDifferentValues(false, false);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrueForDifferentValues() {
        // given

        // when
        final boolean result = booleanValueChanger.areDifferentValues(false, true);

        // then
        assertThat(result).isTrue();
    }

    private Object[] getValuesForTest() {
        return new Object[]{Boolean.FALSE,
                            Boolean.TRUE};
    }
}