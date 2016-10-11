package pl.pojo.tester.internal.field.collections.collection;


import java.util.TreeSet;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class TreeSetChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final TreeSet<String> value = new TreeSet<>();
        final Class<? extends TreeSet> type = value.getClass();

        final TreeSetValueChanger valueChanger = new TreeSetValueChanger();

        // when
        final TreeSet<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final TreeSet<String> value = null;
        final Class<TreeSet> type = TreeSet.class;

        final TreeSetValueChanger valueChanger = new TreeSetValueChanger();

        // when
        final TreeSet<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
