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
public class FloatValueChangerTest {

    private final AbstractFieldValueChanger<Float> floatValueChanger = new FloatValueChanger();

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Primitive_Value(final Float value) {
        // given
        final AllFiledTypes helpClass1 = new AllFiledTypes(value);
        final AllFiledTypes helpClass2 = new AllFiledTypes(value);

        // when
        floatValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
        final Float result1 = getInternalState(helpClass1, "floatType");
        final Float result2 = getInternalState(helpClass2, "floatType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Wrapped_Value(final Float value) {
        // given
        final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
        final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

        // when
        floatValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
        final Float result1 = getInternalState(helpClass1, "floatType");
        final Float result2 = getInternalState(helpClass2, "floatType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForAreDifferent")
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final Float value1,
                                                                                final Float value2,
                                                                                final boolean expectedResult) {
        // given

        // when
        final boolean result = floatValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Object[][] getValuesForAreDifferent() {
        return new Object[][]{
                {null, null, false},
                {(float) 0, (float) 0, false},
                {Float.MIN_VALUE, Float.MIN_VALUE, false},
                {Float.MAX_VALUE, Float.MAX_VALUE, false},

                {(float) 0, (float) 1, true},
                {(float) 0, null, true},
                {null, Float.MIN_VALUE, true},
                {Float.MIN_VALUE, Float.MAX_VALUE, true},
                {Float.MAX_VALUE, Float.MIN_VALUE, true},

                };
    }

    private Object[] getValuesForChangeValue() {
        return new Object[]{Float.MAX_VALUE,
                            Float.MIN_VALUE,
                            Float.MIN_NORMAL,
                            0f,
                            1f,
                            -1f};
    }
}
