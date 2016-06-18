package org.pojo.tester.field.collections.collection;


import org.junit.Test;

import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;

public class VectorChangerTest {

    @Test
    public void Should_Return_Null_When_Value_Is_Not_Null() {
        // given
        final Vector<String> value = new Vector<>();
        final Class<? extends Vector> type = value.getClass();

        final VectorValueChanger valueChanger = new VectorValueChanger();

        // when
        final Vector<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Instance_When_Value_Is_Null() {
        // given
        final Vector<String> value = null;
        final Class<Vector> type = Vector.class;

        final VectorValueChanger valueChanger = new VectorValueChanger();

        // when
        final Vector<?> result = valueChanger.increaseValue(value, type);

        // then
        assertThat(result).isInstanceOf(type);
    }
}
