package pl.pojo.tester.internal.field.collections.collection;


import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;


class VectorChangerTest {

    @Test
    void Should_Return_Null_When_Value_Is_Not_Null_And_Not_Empty() {
        // given
        final Vector<String> value = new Vector<>();
        value.add("test");
        final Class<? extends Vector> type = value.getClass();

        final VectorValueChanger valueChanger = new VectorValueChanger();

        // when
        final Vector<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final Vector<String> value = null;
        final Class<Vector> type = Vector.class;

        final VectorValueChanger valueChanger = new VectorValueChanger();

        // when
        final Vector<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }

    @Test
    void Should_Return_Any_Instance_When_Value_Is_Empty() {
        // given
        final Vector<String> value = new Vector<>();
        final Class<Vector> type = Vector.class;

        final VectorValueChanger valueChanger = new VectorValueChanger();

        // when
        final Vector<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
