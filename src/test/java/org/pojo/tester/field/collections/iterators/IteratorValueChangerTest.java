package org.pojo.tester.field.collections.iterators;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class IteratorValueChangerTest {

    @Test
    @Parameters(method = "getValuesForAreDifferent")
    public void Should_Return_True_Or_False_Whether_Values_Are_Different_Or_Not(final Iterator<?> value1,
                                                                                final Iterator<?> value2,
                                                                                final boolean expectedResult) {
        // given
        final IteratorValueChanger changer = new IteratorValueChanger();

        // when
        final boolean result = changer.areDifferentValues(value1, value2);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
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
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final Iterator<String> value = null;
        final Class<Iterator> type = Iterator.class;
        final IteratorValueChanger valueChanger = new IteratorValueChanger();

        // when
        final Iterator<?> result = valueChanger.increaseValue(value, type);

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
                {emptyArrayList.iterator(), emptyArrayList.iterator(), false},
                {collectionABC.iterator(), collectionABC.iterator(), false},
                {null, emptyArrayList.iterator(), true},
                {collectionAB.iterator(), collectionABC.iterator(), true},
                };
    }

}
