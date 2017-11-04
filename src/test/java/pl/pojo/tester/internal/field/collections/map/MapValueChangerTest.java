package pl.pojo.tester.internal.field.collections.map;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class MapValueChangerTest {

    @Test
    void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final Map<String, String> value = new HashMap<>();
        final Class<? extends Map> type = value.getClass();

        final MapValueChanger valueChanger = new MapValueChanger();

        // when
        final Map<?, ?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    void Should_Return_Any_Instance_When_Value_Is_Null() {
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
