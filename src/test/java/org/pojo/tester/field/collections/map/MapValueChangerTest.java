package org.pojo.tester.field.collections.map;

import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MapValueChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final Map<String, String> value = Collections.EMPTY_MAP;
        final Class<? extends Map> type = value.getClass();

        final MapValueChanger valueChanger = new MapValueChanger();

        // when
        final Map<?, ?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final Map<String, String> value = null;
        final Class<Map> type = Map.class;

        final MapValueChanger valueChanger = new MapValueChanger();

        // when
        final Map<?, ?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
