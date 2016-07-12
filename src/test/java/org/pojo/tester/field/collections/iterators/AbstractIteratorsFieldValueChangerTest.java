package org.pojo.tester.field.collections.iterators;


import java.lang.reflect.Field;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.TestFactory;
import test.fields.collections.iterators.Iterators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;


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
            final boolean result = testCase.valueChanger.canChange(testCase.field);

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
