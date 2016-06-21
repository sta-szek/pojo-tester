package org.pojo.tester.field.collections.collection;


import java.util.LinkedList;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LinkedListValueChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final LinkedList<String> value = new LinkedList<>();
        final Class<? extends LinkedList> type = value.getClass();

        final LinkedListValueChanger valueChanger = new LinkedListValueChanger();

        // when
        final LinkedList<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final LinkedList<String> value = null;
        final Class<LinkedList> type = LinkedList.class;

        final LinkedListValueChanger valueChanger = new LinkedListValueChanger();

        // when
        final LinkedList<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
