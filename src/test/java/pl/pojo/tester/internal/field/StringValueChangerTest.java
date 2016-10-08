package pl.pojo.tester.internal.field;

import classesForTest.fields.AllFiledTypes;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitPlatform.class)

public class StringValueChangerTest {

    private final AbstractFieldValueChanger<String> valueChanger = new StringValueChanger();

    @Test
    public void Should_Change_Value() {
        // given
        final AllFiledTypes helpClass1 = new AllFiledTypes("test");
        final AllFiledTypes helpClass2 = new AllFiledTypes("test");

        // when
        valueChanger.changeFieldsValues(helpClass1,
                                        helpClass2,
                                        Lists.newArrayList(AllFiledTypes.class.getDeclaredFields()));
        final String result1 = getInternalState(helpClass1, "stringType");
        final String result2 = getInternalState(helpClass2, "stringType");

        // then
        assertThat(result1).isNotEqualTo(result2);
    }

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        return Stream.of(new TestCase(null, null, false),
                         new TestCase("", "", false),
                         new TestCase("same", "same", false),
                         new TestCase("", null, true),
                         new TestCase(null, "", true),
                         new TestCase("", "test", true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    public Executable Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final TestCase value) {
        return () -> {
            // when
            final boolean result = valueChanger.areDifferentValues(value.value1, value.value2);

            // then
            assertThat(result).isEqualTo(value.result);
        };
    }

    @AllArgsConstructor
    private class TestCase {
        private String value1;
        private String value2;
        private boolean result;
    }
}