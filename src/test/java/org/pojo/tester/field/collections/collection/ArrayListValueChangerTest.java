package org.pojo.tester.field.collections.collection;


import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ArrayListValueChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final ArrayList<String> value = new ArrayList<>();
        final Class<? extends ArrayList> type = value.getClass();

        final ArrayListValueChanger valueChanger = new ArrayListValueChanger();

        // when
        final ArrayList<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final ArrayList<String> value = null;
        final Class<ArrayList> type = ArrayList.class;

        final ArrayListValueChanger valueChanger = new ArrayListValueChanger();

        // when
        final ArrayList<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
