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
public class DoubleValueChangerTest {

    private final AbstractFieldValueChanger<Double> doubleValueChanger = new DoubleValueChanger();

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Primitive_Value(final Double value) {
        // given
        final AllFiledTypes helpClass1 = new AllFiledTypes(value);
        final AllFiledTypes helpClass2 = new AllFiledTypes(value);

        // when
        doubleValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
        final Double result1 = getInternalState(helpClass1, "doubleType");
        final Double result2 = getInternalState(helpClass2, "doubleType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Wrapped_Value(final Double value) {
        // given
        final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
        final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

        // when
        doubleValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
        final Double result1 = getInternalState(helpClass1, "doubleType");
        final Double result2 = getInternalState(helpClass2, "doubleType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForAreDifferent")
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final Double value1,
                                                                                final Double value2,
                                                                                final boolean expectedResult) {
        // given

        // when
        final boolean result = doubleValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Object[][] getValuesForAreDifferent() {
        return new Object[][]{
                {null, null, false},
                {(double) 0, (double) 0, false},
                {Double.MIN_VALUE, Double.MIN_VALUE, false},
                {Double.MAX_VALUE, Double.MAX_VALUE, false},

                {(double) 0, (double) 1, true},
                {(double) 0, null, true},
                {null, Double.MIN_VALUE, true},
                {Double.MIN_VALUE, Double.MAX_VALUE, true},
                {Double.MAX_VALUE, Double.MIN_VALUE, true},

                };
    }

    private Object[] getValuesForChangeValue() {
        return new Object[]{Double.MAX_VALUE,
                            Double.MIN_VALUE,
                            Double.MIN_NORMAL,
                            0D,
                            1D,
                            -1D};
    }
}
