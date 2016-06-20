package org.pojo.tester.field.primitive;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.AbstractFieldValueChanger;
import test.fields.AllFiledTypes;
import test.fields.AllFiledTypes_Wrapped;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitParamsRunner.class)
public class BooleanValueChangerTest {

    private final AbstractFieldValueChanger<Boolean> booleanValueChanger = new BooleanValueChanger();

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Primitive_Value(final Boolean value) {
        // given
        final AllFiledTypes helpClass1 = new AllFiledTypes(value);
        final AllFiledTypes helpClass2 = new AllFiledTypes(value);

        // when
        booleanValueChanger.changeFieldsValues(helpClass1, helpClass2, newArrayList(AllFiledTypes.class.getDeclaredFields()));
        final Boolean result1 = getInternalState(helpClass1, "booleanType");
        final Boolean result2 = getInternalState(helpClass2, "booleanType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForChangeValue")
    public void Should_Change_Wrapped_Value(final Boolean value) {
        // given
        final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
        final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

        // when
        booleanValueChanger.changeFieldsValues(helpClass1, helpClass2, newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
        final Boolean result1 = getInternalState(helpClass1, "booleanType");
        final Boolean result2 = getInternalState(helpClass2, "booleanType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForAreDifferent")
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final Boolean value1,
                                                                                final Boolean value2,
                                                                                final boolean expectedResult) {
        // given

        // when
        final boolean result = booleanValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Object[][] getValuesForAreDifferent() {
        return new Object[][]{
                {null, null, false},
                {false, false, false},
                {Boolean.FALSE, Boolean.FALSE, false},
                {Boolean.TRUE, Boolean.TRUE, false},

                {false, true, true},
                {false, null, true},
                {null, Boolean.FALSE, true},
                {Boolean.FALSE, Boolean.TRUE, true},
                {Boolean.TRUE, Boolean.FALSE, true},

                };
    }

    private Object[] getValuesForChangeValue() {
        return new Object[]{Boolean.FALSE,
                            Boolean.TRUE};
    }
}
