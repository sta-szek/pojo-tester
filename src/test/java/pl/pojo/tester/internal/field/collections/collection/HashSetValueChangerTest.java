package pl.pojo.tester.internal.field.collections.collection;


import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;


public class HashSetValueChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null_And_Not_Empty() {
        // given
        final HashSet<String> value = new HashSet<>();
        value.add("test");
        final Class<? extends HashSet> type = value.getClass();

        final HashSetValueChanger valueChanger = new HashSetValueChanger();

        // when
        final HashSet<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final HashSet<String> value = null;
        final Class<HashSet> type = HashSet.class;

        final HashSetValueChanger valueChanger = new HashSetValueChanger();

        // when
        final HashSet<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Empty() {
        // given
        final HashSet<String> value = new HashSet<>();
        final Class<HashSet> type = HashSet.class;

        final HashSetValueChanger valueChanger = new HashSetValueChanger();

        // when
        final HashSet<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
