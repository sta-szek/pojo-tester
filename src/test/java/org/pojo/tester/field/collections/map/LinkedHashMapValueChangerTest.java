package org.pojo.tester.field.collections.map;

import java.util.LinkedHashMap;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class LinkedHashMapValueChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final LinkedHashMap<String, String> value = new LinkedHashMap<>();
        final Class<? extends LinkedHashMap> type = value.getClass();

        final LinkedHashMapValueChanger valueChanger = new LinkedHashMapValueChanger();

        // when
        final LinkedHashMap<?, ?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final LinkedHashMap<String, String> value = null;
        final Class<LinkedHashMap> type = LinkedHashMap.class;

        final LinkedHashMapValueChanger valueChanger = new LinkedHashMapValueChanger();

        // when
        final LinkedHashMap<?, ?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
