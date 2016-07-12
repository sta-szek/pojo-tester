package org.pojo.tester.field;

import java.lang.reflect.Field;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import test.fields.AllFiledTypes;
import test.fields.AllFiledTypes_Wrapped;
import test.fields.EnumFields;
import test.fields.EnumWithoutConstants;
import test.fields.SingleEnum;
import test.fields.TestEnum1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.powermock.reflect.Whitebox.getInternalState;
import static test.TestHelper.getDefaultDisplayName;


public class EnumValueChangerTest {

    private final AbstractFieldValueChanger<Enum> valueChanger = new EnumValueChanger();

    @TestFactory
    public Stream<DynamicTest> Should_Change_Enum_Value() {
        return Stream.of(TestEnum1.ENUM1, TestEnum1.ENUM2)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Enum_Value(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Can_Change_Or_Not() throws NoSuchFieldException {
        return Stream.of(new CanChangeCase(EnumFields.class.getDeclaredField("nullEnum"), true),
                         new CanChangeCase(EnumFields.class.getDeclaredField("testEnum1"), true),
                         new CanChangeCase(EnumFields.class.getDeclaredField("singleEnum1"), true),
                         new CanChangeCase(AllFiledTypes.class.getDeclaredField("intType"), false),
                         new CanChangeCase(AllFiledTypes_Wrapped.class.getDeclaredField("intType"), false),
                         new CanChangeCase(EnumFields.class.getDeclaredField("object"), false))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.field.getName()),
                                               Should_Return_True_Or_False_Whether_Can_Change_Or_Not(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        return Stream.of(new AreDifferentCase(null, null, false),
                         new AreDifferentCase(TestEnum1.ENUM1, TestEnum1.ENUM1, false),
                         new AreDifferentCase(TestEnum1.ENUM2, TestEnum1.ENUM2, false),
                         new AreDifferentCase(TestEnum1.ENUM2, TestEnum1.ENUM2, false),
                         new AreDifferentCase(TestEnum1.ENUM2, null, true),
                         new AreDifferentCase(null, TestEnum1.ENUM2, true),
                         new AreDifferentCase(TestEnum1.ENUM2, null, true),
                         new AreDifferentCase(TestEnum1.ENUM2, TestEnum1.ENUM1, true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    @Test
    public void Should_Throw_Exception_When_Enum_Has_No_Constants() {
        // given

        // when
        final Throwable result = catchThrowable(() -> valueChanger.increaseValue(null, EnumWithoutConstants.class));

        // then
        assertThat(result).isInstanceOf(ImpossibleEnumValueChangeException.class);
    }

    @Test
    public void Should_Return_Null_For_Single_Enum_Constant() {
        // given

        // when
        final Enum result = valueChanger.increaseValue(SingleEnum.ENUM1, SingleEnum.class);

        // then
        assertThat(result).isNull();
    }

    private Executable Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final AreDifferentCase testCase) {
        return () -> {
            // when
            final boolean result = valueChanger.areDifferentValues(testCase.value1, testCase.value2);

            // then
            assertThat(result).isEqualTo(testCase.result);
        };
    }

    private Executable Should_Return_True_Or_False_Whether_Can_Change_Or_Not(final CanChangeCase testCase) {
        return () -> {
            // when
            final boolean result = valueChanger.canChange(testCase.field);

            // then
            assertThat(result).isEqualTo(testCase.result);
        };
    }

    private Executable Should_Change_Enum_Value(final TestEnum1 value) {
        return () -> {
            // given
            final EnumFields helpClass1 = new EnumFields(value);
            final EnumFields helpClass2 = new EnumFields(value);

            // when
            valueChanger.changeFieldsValues(helpClass1, helpClass2, newArrayList(EnumFields.class.getDeclaredFields()));
            final TestEnum1 result1 = getInternalState(helpClass1, "testEnum1");
            final TestEnum1 result2 = getInternalState(helpClass2, "testEnum1");

            // then
            assertThat(result1).isNotEqualTo(result2);
        };
    }

    @AllArgsConstructor
    private class CanChangeCase {

        private Field field;
        private boolean result;
    }

    @AllArgsConstructor
    private class AreDifferentCase {

        private TestEnum1 value1;
        private TestEnum1 value2;
        private boolean result;
    }
}
