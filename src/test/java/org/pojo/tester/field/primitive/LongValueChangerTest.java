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
public class LongValueChangerTest {

    private final AbstractFieldValueChanger<Long> longValueChanger = new LongValueChanger();

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Primitive_Value(final Long value) {
        // given
        final AllFiledTypes helpClass1 = new AllFiledTypes(value);
        final AllFiledTypes helpClass2 = new AllFiledTypes(value);

        // when
        longValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
        final Long result1 = getInternalState(helpClass1, "longType");
        final Long result2 = getInternalState(helpClass2, "longType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Wrapped_Value(final Long value) {
        // given
        final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
        final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

        // when
        longValueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
        final Long result1 = getInternalState(helpClass1, "longType");
        final Long result2 = getInternalState(helpClass2, "longType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForAreDifferent")
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final Long value1,
                                                                                final Long value2,
                                                                                final boolean expectedResult) {
        // given

        // when
        final boolean result = longValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Object[][] getValuesForAreDifferent() {
        return new Object[][]{
                {null, null, false},
                {(long) 0, (long) 0, false},
                {Long.MIN_VALUE, Long.MIN_VALUE, false},
                {Long.MAX_VALUE, Long.MAX_VALUE, false},

                {(long) 0, (long) 1, true},
                {(long) 0, null, true},
                {null, Long.MIN_VALUE, true},
                {Long.MIN_VALUE, Long.MAX_VALUE, true},
                {Long.MAX_VALUE, Long.MIN_VALUE, true},

                };
    }

    private Object[] getValuesForChangeValue() {
        return new Object[]{Long.MAX_VALUE,
                            Long.MIN_VALUE,
                            0L,
                            -1L,
                            1L};
    }
}
