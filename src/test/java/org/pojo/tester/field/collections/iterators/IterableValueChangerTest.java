package org.pojo.tester.field.collections.iterators;


import java.util.ArrayList;
import java.util.Collection;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class IterableValueChangerTest {

    @Test
    @Parameters(method = "getValuesForAreDifferent")
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final Iterable<?> value1,
                                                                                final Iterable<?> value2,
                                                                                final boolean expectedResult) {
        // given
        final IterableValueChanger changer = new IterableValueChanger();

        // when
        final boolean result = changer.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
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

    private Object[][] getValuesForAreDifferent() {
        final Collection collectionABC = new ArrayList<>();
        collectionABC.add("A");
        collectionABC.add("B");
        collectionABC.add("C");

        final Collection collectionAB = new ArrayList<>();
        collectionAB.add("A");
        collectionAB.add("B");

        final ArrayList<Object> emptyArrayList = new ArrayList<>();

        return new Object[][]{
                {null, null, false},
                {emptyArrayList, emptyArrayList, false},
                {collectionABC, collectionABC, false},
                {null, emptyArrayList, true},
                {collectionAB, collectionABC, true},
                };
    }

}
