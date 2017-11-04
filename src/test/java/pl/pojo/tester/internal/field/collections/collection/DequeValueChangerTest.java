package pl.pojo.tester.internal.field.collections.collection;


import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;


class DequeValueChangerTest {

    @Test
    void Should_Return_Null_When_Value_Is_Not_Null_And_Not_Empty() {
        // given
        final Deque<String> value = new LinkedList<>();
        value.add("test");
        final Class<? extends Deque> type = value.getClass();

        final DequeValueChanger valueChanger = new DequeValueChanger();

        // when
        final Deque<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final Deque<String> value = null;
        final Class<Deque> type = Deque.class;

        final DequeValueChanger valueChanger = new DequeValueChanger();

        // when
        final Deque<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }

    @Test
    void Should_Return_Any_Instance_When_Value_Is_Empty() {
        // given
        final Deque<String> value = new LinkedList<>();
        final Class<Deque> type = Deque.class;

        final DequeValueChanger valueChanger = new DequeValueChanger();

        // when
        final Deque<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
