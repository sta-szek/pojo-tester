package pl.pojo.tester.internal.field.collections.collection;


import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;


public class QueueChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null_And_Not_Empty() {
        // given
        final Queue<String> value = new LinkedList<>();
        value.add("test");
        final Class<? extends Queue> type = value.getClass();

        final QueueValueChanger valueChanger = new QueueValueChanger();

        // when
        final Queue<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final Queue<String> value = null;
        final Class<Queue> type = Queue.class;

        final QueueValueChanger valueChanger = new QueueValueChanger();

        // when
        final Queue<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Empty() {
        // given
        final Queue<String> value = new LinkedList<>();
        final Class<Queue> type = Queue.class;

        final QueueValueChanger valueChanger = new QueueValueChanger();

        // when
        final Queue<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
