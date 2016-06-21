package org.pojo.tester.field.collections.collection;


import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SetChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final Set<String> value = Collections.EMPTY_SET;
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
}
