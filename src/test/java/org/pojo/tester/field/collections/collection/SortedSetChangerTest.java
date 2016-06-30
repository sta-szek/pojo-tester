package org.pojo.tester.field.collections.collection;


import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class SortedSetChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final SortedSet<String> value = new TreeSet<>();
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
}
