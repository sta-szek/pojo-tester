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
public class BooleanValueChangerTest {

    private final AbstractFieldsValuesChanger<Boolean> booleanValueChanger = new BooleanValueChanger();

    @Test
    @Parameters(method = "getValuesForTest")
    public void shouldChangeValue(final Boolean value) {
        // given
        final AllFiledTypes helpClass1 = new AllFiledTypes(value);
        final AllFiledTypes helpClass2 = new AllFiledTypes(value);

        // when
        booleanValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
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