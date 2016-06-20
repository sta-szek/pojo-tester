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
public class IntegerValueChangerTest {

    private final AbstractFieldValueChanger<Integer> integerValueChanger = new IntegerValueChanger();

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Primitive_Value(final Integer value) {
        // given
        final AllFiledTypes helpClass1 = new AllFiledTypes(value);
        final AllFiledTypes helpClass2 = new AllFiledTypes(value);

        // when
        integerValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
        final Integer result1 = getInternalState(helpClass1, "intType");
        final Integer result2 = getInternalState(helpClass2, "intType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Wrapped_Value(final Integer value) {
        // given
        final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
        final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

        // when
        integerValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
        final Integer result1 = getInternalState(helpClass1, "intType");
        final Integer result2 = getInternalState(helpClass2, "intType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForAreDifferent")
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final Integer value1,
                                                                                final Integer value2,
                                                                                final boolean expectedResult) {
        // given

        // when
        final boolean result = integerValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Object[][] getValuesForAreDifferent() {
        return new Object[][]{
                {null, null, false},
                {0, 0, false},
                {Integer.MIN_VALUE, Integer.MIN_VALUE, false},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, false},

                {0, 1, true},
                {0, null, true},
                {null, Integer.MIN_VALUE, true},
                {Integer.MIN_VALUE, Integer.MAX_VALUE, true},
                {Integer.MAX_VALUE, Integer.MIN_VALUE, true},

                };
    }

    private Object[] getValuesForChangeValue() {
        return new Object[]{Integer.MAX_VALUE,
                            Integer.MIN_VALUE,
                            0,
                            -1,
                            1};
    }
}
