package org.pojo.tester.field.collections.collection;


import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueueChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final Queue<String> value = new LinkedList<>();
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
}
