package pl.pojo.tester.field.collections.collection;


import java.util.LinkedHashSet;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class LinkedHashSetValueChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final LinkedHashSet<String> value = new LinkedHashSet<>();
        final Class<? extends LinkedHashSet> type = value.getClass();

        final LinkedHashSetValueChanger valueChanger = new LinkedHashSetValueChanger();

        // when
        final LinkedHashSet<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final LinkedHashSet<String> value = null;
        final Class<LinkedHashSet> type = LinkedHashSet.class;

        final LinkedHashSetValueChanger valueChanger = new LinkedHashSetValueChanger();

        // when
        final LinkedHashSet<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
