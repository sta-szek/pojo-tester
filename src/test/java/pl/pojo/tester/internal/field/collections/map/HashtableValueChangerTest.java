package pl.pojo.tester.internal.field.collections.map;

import org.junit.jupiter.api.Test;

import java.util.Hashtable;

import static org.assertj.core.api.Assertions.assertThat;


class HashtableValueChangerTest {

    @Test
    void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final Hashtable<String, String> value = new Hashtable<>();
        final Class<? extends Hashtable> type = value.getClass();

        final HashtableValueChanger valueChanger = new HashtableValueChanger();

        // when
        final Hashtable<?, ?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final Hashtable<String, String> value = null;
        final Class<Hashtable> type = Hashtable.class;

        final HashtableValueChanger valueChanger = new HashtableValueChanger();

        // when
        final Hashtable<?, ?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
