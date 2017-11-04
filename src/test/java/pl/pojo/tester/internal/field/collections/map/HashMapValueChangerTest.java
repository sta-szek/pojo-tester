package pl.pojo.tester.internal.field.collections.map;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


class HashMapValueChangerTest {

    @Test
    void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final HashMap<String, String> value = new HashMap<>();
        final Class<? extends HashMap> type = value.getClass();

        final HashMapValueChanger valueChanger = new HashMapValueChanger();

        // when
        final HashMap<?, ?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final HashMap<String, String> value = null;
        final Class<HashMap> type = HashMap.class;

        final HashMapValueChanger valueChanger = new HashMapValueChanger();

        // when
        final HashMap<?, ?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
