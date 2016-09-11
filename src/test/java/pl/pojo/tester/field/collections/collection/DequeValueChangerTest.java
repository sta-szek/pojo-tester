package pl.pojo.tester.field.collections.collection;


import java.util.Deque;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class DequeValueChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final Deque<String> value = new LinkedList<>();
        final Class<? extends Deque> type = value.getClass();

        final DequeValueChanger valueChanger = new DequeValueChanger();

        // when
        final Deque<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final Deque<String> value = null;
        final Class<Deque> type = Deque.class;

        final DequeValueChanger valueChanger = new DequeValueChanger();

        // when
        final Deque<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
