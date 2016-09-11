package pl.pojo.tester.field.collections.map;

import java.util.TreeMap;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class TreeMapValueChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final TreeMap<String, String> value = new TreeMap<>();
        final Class<? extends TreeMap> type = value.getClass();

        final TreeMapValueChanger valueChanger = new TreeMapValueChanger();

        // when
        final TreeMap<?, ?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final TreeMap<String, String> value = null;
        final Class<TreeMap> type = TreeMap.class;

        final TreeMapValueChanger valueChanger = new TreeMapValueChanger();

        // when
        final TreeMap<?, ?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
