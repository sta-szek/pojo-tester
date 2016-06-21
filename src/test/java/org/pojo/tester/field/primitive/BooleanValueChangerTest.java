package org.pojo.tester.field.primitive;


import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.TestFactory;
import org.pojo.tester.field.AbstractFieldValueChanger;
import test.fields.AllFiledTypes;
import test.fields.AllFiledTypes_Wrapped;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.powermock.reflect.Whitebox.getInternalState;
import static test.TestHelper.getDefaultDisplayName;

public class BooleanValueChangerTest {

    private final AbstractFieldValueChanger<Boolean> valueChanger = new BooleanValueChanger();

    @TestFactory
    public Stream<DynamicTest> Should_Change_Primitive_Value() {
        return Stream.of(Boolean.TRUE, Boolean.FALSE)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Primitive_Value(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Change_Wrapped_Value() {
        return Stream.of(Boolean.TRUE, Boolean.FALSE)
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Change_Wrapped_Value(value)));
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        return Stream.of(new TestCase(null, null, false),
                         new TestCase(false, false, false),
                         new TestCase(Boolean.FALSE, Boolean.FALSE, false),
                         new TestCase(Boolean.TRUE, Boolean.TRUE, false),
                         new TestCase(false, true, true),
                         new TestCase(false, null, true),
                         new TestCase(null, Boolean.FALSE, true),
                         new TestCase(Boolean.FALSE, Boolean.TRUE, true),
                         new TestCase(Boolean.TRUE, Boolean.FALSE, true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    private Executable Should_Change_Primitive_Value(final Boolean value) {
        return () -> {
            // given
            final AllFiledTypes helpClass1 = new AllFiledTypes(value);
            final AllFiledTypes helpClass2 = new AllFiledTypes(value);

            // when
            valueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
            final Boolean result1 = getInternalState(helpClass1, "booleanType");
            final Boolean result2 = getInternalState(helpClass2, "booleanType");

            // then
            assertThat(result1).isNotEqualTo(result2);
        };
    }

    private Executable Should_Change_Wrapped_Value(final Boolean value) {
        return () -> {
            // given
            final AllFiledTypes_Wrapped helpClass1 = new AllFiledTypes_Wrapped(value);
            final AllFiledTypes_Wrapped helpClass2 = new AllFiledTypes_Wrapped(value);

            // when
            valueChanger.changeFieldsValues(helpClass1, helpClass2, Lists.newArrayList(AllFiledTypes_Wrapped.class.getDeclaredFields()));
            final Boolean result1 = getInternalState(helpClass1, "booleanType");
            final Boolean result2 = getInternalState(helpClass2, "booleanType");

            // then
            assertThat(result1).isNotEqualTo(result2);
        };
    }

    private Executable Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final TestCase value) {
        return () -> {
            // when
            final boolean result = valueChanger.areDifferentValues(value.value1, value.value2);

            // then
            assertThat(result).isEqualTo(value.result);
        };
    }

    @AllArgsConstructor
    private class TestCase {
        private Boolean value1;
        private Boolean value2;
        private boolean result;
    }

}
