package org.pojo.tester.field.collections.collection;


import java.util.Stack;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class StackChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final Stack<String> value = new Stack<>();
        final Class<? extends Stack> type = value.getClass();

        final StackValueChanger valueChanger = new StackValueChanger();

        // when
        final Stack<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final Stack<String> value = null;
        final Class<Stack> type = Stack.class;

        final StackValueChanger valueChanger = new StackValueChanger();

        // when
        final Stack<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
