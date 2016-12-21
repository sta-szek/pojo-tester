package pl.pojo.tester.internal.field.collections.collection;


import org.junit.jupiter.api.Test;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;


public class SortedSetChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null_And_Not_Empty() {
        // given
        final SortedSet<String> value = new TreeSet<>();
        value.add("test");
        final Class<? extends SortedSet> type = value.getClass();

        final SortedSetValueChanger valueChanger = new SortedSetValueChanger();

        // when
        final SortedSet<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final SortedSet<String> value = null;
        final Class<SortedSet> type = SortedSet.class;

        final SortedSetValueChanger valueChanger = new SortedSetValueChanger();

        // when
        final SortedSet<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Empty() {
        // given
        final SortedSet<String> value = new TreeSet<>();
        final Class<SortedSet> type = SortedSet.class;

        final SortedSetValueChanger valueChanger = new SortedSetValueChanger();

        // when
        final SortedSet<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
