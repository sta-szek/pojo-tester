package pl.pojo.tester.internal.field.collections.collection;


import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class SetChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null_And_Not_Empty() {
        // given
        final Set<String> value = new HashSet<>();
        value.add("test");
        final Class<? extends Set> type = value.getClass();

        final SetValueChanger valueChanger = new SetValueChanger();

        // when
        final Set<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final Set<String> value = null;
        final Class<Set> type = Set.class;

        final SetValueChanger valueChanger = new SetValueChanger();

        // when
        final Set<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Empty() {
        // given
        final Set<String> value = new HashSet<>();
        final Class<Set> type = Set.class;

        final SetValueChanger valueChanger = new SetValueChanger();

        // when
        final Set<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
