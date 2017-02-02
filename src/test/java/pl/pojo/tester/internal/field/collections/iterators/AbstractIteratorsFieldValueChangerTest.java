package pl.pojo.tester.internal.field.collections.iterators;


import classesForTest.fields.collections.iterators.Iterators;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


public class AbstractIteratorsFieldValueChangerTest {

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Can_Change_Or_Not() throws NoSuchFieldException {
        return Stream.of(new CanChangeCase(new IteratorValueChanger(), Iterators.class.getDeclaredField("iterator"), true),
                         new CanChangeCase(new IterableValueChanger(), Iterators.class.getDeclaredField("iterable"), true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.field.getName()),
                                               Should_Return_True_Or_False_Whether_Can_Change_Or_Not(value)));
    }

    private Executable Should_Return_True_Or_False_Whether_Can_Change_Or_Not(final CanChangeCase testCase) {
        return () -> {
            // when
            final boolean result = testCase.valueChanger.canChange(testCase.field.getType());

            // then
            assertThat(result).isEqualTo(testCase.result);
        };
    }

    @AllArgsConstructor
    private class CanChangeCase {
        private AbstractIteratorsFieldValueChanger valueChanger;
        private Field field;
        private boolean result;
    }
}
