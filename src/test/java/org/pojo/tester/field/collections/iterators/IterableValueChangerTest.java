package org.pojo.tester.field.collections.iterators;


import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static test.TestHelper.getDefaultDisplayName;

@RunWith(JUnitPlatform.class)
public class IterableValueChangerTest {

    @TestFactory
    public Stream<DynamicTest> Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not() {
        final Collection<String> collectionABC = new ArrayList<>();
        collectionABC.add("A");
        collectionABC.add("B");
        collectionABC.add("C");

        final Collection<String> collectionAB_1 = new ArrayList<>();
        collectionAB_1.add("A");
        collectionAB_1.add("B");

        final Collection<String> collectionAB_2 = new ArrayList<>();
        collectionAB_2.add("A");
        collectionAB_2.add("B");

        final ArrayList<Object> emptyArrayList = new ArrayList<>();

        return Stream.of(new AreDifferentCase(null, null, false),
                         new AreDifferentCase(emptyArrayList, emptyArrayList, false),
                         new AreDifferentCase(collectionABC, collectionABC, false),
                         new AreDifferentCase(null, emptyArrayList, true),
                         new AreDifferentCase(emptyArrayList, null, true),
                         new AreDifferentCase(collectionAB_1, collectionABC, true),
                         new AreDifferentCase(collectionAB_1, collectionAB_2, false))
                     .map(value -> dynamicTest(getDefaultDisplayName(value.value1 + " " + value.value2),
                                               Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(value)));
    }

    public Executable Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final AreDifferentCase testCase) {
        return () -> {
            // given
            final IterableValueChanger valueChanger = new IterableValueChanger();

            // when
            final boolean result = valueChanger.areDifferentValues(testCase.value1, testCase.value2);

            // then
            assertThat(result).isEqualTo(testCase.result);
        };
    }

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final Collection<String> value = new ArrayList<>();
        final Class<? extends Collection> type = value.getClass();

        final IterableValueChanger valueChanger = new IterableValueChanger();

        // when
        final Iterable<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final Collection<String> value = null;
        final Class<Iterable> type = Iterable.class;
        final IterableValueChanger valueChanger = new IterableValueChanger();

        // when
        final Iterable<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }

    @AllArgsConstructor
    private class AreDifferentCase {
        private Collection value1;
        private Collection value2;
        private boolean result;
    }

}
