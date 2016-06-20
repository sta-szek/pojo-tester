package org.pojo.tester.field;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.fields.*;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitParamsRunner.class)
public class EnumValueChangerTest {
    private final AbstractFieldValueChanger<Enum> enumValueChanger = new EnumValueChanger();

    @Test
    @Parameters(method = "getValuesForTest")
    public void Should_Change_Enum_Value(final TestEnum1 value) {
        // given
        final EnumFields helpClass1 = new EnumFields(value);
        final EnumFields helpClass2 = new EnumFields(value);

        // when
        enumValueChanger.changeFieldsValues(helpClass1, helpClass2, newArrayList(EnumFields.class.getDeclaredFields()));
        final TestEnum1 result1 = getInternalState(helpClass1, "testEnum1");
        final TestEnum1 result2 = getInternalState(helpClass2, "testEnum1");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @Test
    @Parameters(method = "getValuesForCanChange")
    public void Should_Return_True_Or_False_Whether_Can_Change_Or_Not(final Field field, final boolean expectedResult) {
        // given

        // when
        final boolean result = enumValueChanger.canChange(field);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void Should_Throw_Exception_When_Enum_Has_No_Constants() {
        // given

        // when
        final Throwable result = catchThrowable(() -> enumValueChanger.increaseValue(null, EnumWithoutConstants.class));

        // then
        assertThat(result).isInstanceOf(ImpossibleEnumValueChangeException.class);
    }

    @Test
    public void Should_Return_Null_For_Single_Enum_Constant() {
        // given

        // when
        final Enum result = enumValueChanger.increaseValue(SingleEnum.ENUM1, SingleEnum.class);

        // then
        assertThat(result).isNull();
    }

    @Test
    @Parameters(method = "getValuesForAreDifferent")
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final TestEnum1 value1,
                                                                                final TestEnum1 value2,
                                                                                final boolean expectedResult) {
        // given
        // when
        final boolean result = enumValueChanger.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private Object[][] getValuesForAreDifferent() {
        return new Object[][]{
                {null, null, false},
                {TestEnum1.ENUM1, TestEnum1.ENUM1, false},
                {TestEnum1.ENUM2, TestEnum1.ENUM2, false},
                {TestEnum1.ENUM2, TestEnum1.ENUM2, false},
                {TestEnum1.ENUM2, null, true},
                {null, TestEnum1.ENUM2, true},
                {TestEnum1.ENUM2, null, true},
                {TestEnum1.ENUM2, TestEnum1.ENUM1, true},
                };
    }

    private Object[][] getValuesForCanChange() throws NoSuchFieldException {
        final Field nullEnum = EnumFields.class.getDeclaredField("nullEnum");
        final Field testEnum = EnumFields.class.getDeclaredField("testEnum1");
        final Field singleEnum1 = EnumFields.class.getDeclaredField("singleEnum1");
        final Field object = EnumFields.class.getDeclaredField("object");
        final Field intType = AllFiledTypes.class.getDeclaredField("intType");
        final Field intWrappedType = AllFiledTypes_Wrapped.class.getDeclaredField("intType");

        return new Object[][]{{nullEnum, true},
                              {testEnum, true},
                              {singleEnum1, true},
                              {intType, false},
                              {intWrappedType, false},
                              {object, false}};
    }

    private Object[] getValuesForTest() {
        return new Object[]{TestEnum1.ENUM1, TestEnum1.ENUM2};
    }
}
