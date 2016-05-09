package org.pojo.tester.field.primitive;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.AbstractFieldsValuesChanger;
import test.fields.AllFiledTypes;
import test.fields.AllFiledTypes_Wrapped;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitParamsRunner.class)
public class BooleanValueChangerTest {

    private final AbstractFieldsValuesChanger<Boolean> booleanValueChanger = new BooleanValueChanger();

    @Test
    @Parameters(method = "getValuesForTest")
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
    @Parameters(method = "getValuesForTest")
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
    public void Should_Return_False_If_Values_Are_Not_Different() {
        // given

        // when
        final boolean result = booleanValueChanger.areDifferentValues(false, false);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void Should_Return_True_If_Values_Are_Different() {
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
