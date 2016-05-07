package org.pojo.tester.field.primitive;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.AbstractFieldsValuesChanger;
import test.fields.AllFiledTypes;
import test.fields.AllFiledTypes_Wrapped;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitParamsRunner.class)
public class ShortValueChangerTest {

    private final AbstractFieldsValuesChanger<Short> shortValueChanger = new ShortValueChanger();

    @Test
    @Parameters(method = "getValuesForTest")
    public void shouldChangePrimitiveValue(final Short value) {
        // given
        final AllFiledTypes helpClass1 = new AllFiledTypes(value);
        final AllFiledTypes helpClass2 = new AllFiledTypes(value);

        // when
        shortValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
        final Short result1 = getInternalState(helpClass1, "shortType");
        final Short result2 = getInternalState(helpClass2, "shortType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForTest")
    public void shouldChangeWrappedValue(final Short value) {
        // given
        final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
        final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

        // when
        shortValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
        final Short result1 = getInternalState(helpClass1, "shortType");
        final Short result2 = getInternalState(helpClass2, "shortType");

        // then
        assertThat(result1).isNotEqualTo(result2);
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
