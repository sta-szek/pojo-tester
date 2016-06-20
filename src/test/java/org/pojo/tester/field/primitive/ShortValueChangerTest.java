package org.pojo.tester.field.primitive;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.AbstractFieldValueChanger;
import test.fields.AllFiledTypes;
import test.fields.AllFiledTypes_Wrapped;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitParamsRunner.class)
public class ShortValueChangerTest {

    private final AbstractFieldValueChanger<Short> shortValueChanger = new ShortValueChanger();

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Primitive_Value(final Short value) {
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
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Wrapped_Value(final Short value) {
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
    @Parameters(method = "getValuesForAreDifferent")
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final Short value1,
                                                                                final Short value2,
                                                                                final boolean expectedResult) {
        // given

        // when
        final boolean result = shortValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Object[][] getValuesForAreDifferent() {
        return new Object[][]{
                {null, null, false},
                {(short) 0, (short) 0, false},
                {Short.MIN_VALUE, Short.MIN_VALUE, false},
                {Short.MAX_VALUE, Short.MAX_VALUE, false},

                {(short) 0, (short) 1, true},
                {(short) 0, null, true},
                {null, Short.MIN_VALUE, true},
                {Short.MIN_VALUE, Short.MAX_VALUE, true},
                {Short.MAX_VALUE, Short.MIN_VALUE, true},

                };
    }

    private Object[] getValuesForChangeValue() {
        return new Object[]{Short.MAX_VALUE,
                            Short.MIN_VALUE,
                            new Short((short) 0),
                            new Short((short) -1),
                            new Short((short) 1)};
    }
}
