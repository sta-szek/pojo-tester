package pl.pojo.tester.internal.field.collections.iterators;


import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


class IteratorValueChangerTest {

    @TestFactory
    Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        final Collection<String> collectionABC = new ArrayList<>();
        collectionABC.add("A");
        collectionABC.add("B");
        collectionABC.add("C");

        final Collection<String> collectionAB = new ArrayList<>();
        collectionAB.add("A");
        collectionAB.add("B");

        final ArrayList<Object> emptyArrayList = new ArrayList<>();

        return Stream.of(new AreDifferentCase(null, null, false),
                         new AreDifferentCase(emptyArrayList.iterator(), emptyArrayList.iterator(), false),
                         new AreDifferentCase(collectionABC.iterator(), collectionABC.iterator(), false),
                         new AreDifferentCase(emptyArrayList.iterator(), null, true),
                         new AreDifferentCase(null, emptyArrayList.iterator(), true),
                         new AreDifferentCase(collectionAB.iterator(), collectionABC.iterator(), true))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    private Executable Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final AreDifferentCase testCase) {
        return () -> {
            // given
            final IteratorValueChanger valueChanger = new IteratorValueChanger();

            // when
            final boolean result = valueChanger.areDifferentValues(testCase.value1, testCase.value2);

            // then
            assertThat(result).isEqualTo(testCase.result);
        };
    }

    @Test
    void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final Iterator<Object> value = new ArrayList<>().iterator();
        final Class<? extends Iterator> type = value.getClass();

        final IteratorValueChanger valueChanger = new IteratorValueChanger();

        // when
        final Iterator<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final Iterator<String> value = null;
        final Class<Iterator> type = Iterator.class;
        final IteratorValueChanger valueChanger = new IteratorValueChanger();

        // when
        final Iterator<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }

    @AllArgsConstructor
    private class AreDifferentCase {
        private Iterator value1;
        private Iterator value2;
        private boolean result;
    }

}
