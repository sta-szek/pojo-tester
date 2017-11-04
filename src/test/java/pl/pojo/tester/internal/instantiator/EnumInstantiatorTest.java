package pl.pojo.tester.internal.instantiator;


import classesForTest.EmptyEnum;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class EnumInstantiatorTest {

    @Test
    void Should_Return_Null_When_Enum_Is_Empty() {
        // given
        final EnumInstantiator instantiator = new EnumInstantiator(EmptyEnum.class);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isNull();
    }

    @Test
    void Should_Return_Any_Enum_Value() {
        // given
        final Class<?> doubleEnumClass = DoubleEnum.class;
        final EnumInstantiator instantiator = new EnumInstantiator(doubleEnumClass);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(doubleEnumClass);
    }

    @Test
    void Should_Return_One_Enum_Value() {
        // given
        final Class<?> oneEnumClass = OneEnum.class;
        final EnumInstantiator instantiator = new EnumInstantiator(oneEnumClass);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(oneEnumClass);
    }

    enum DoubleEnum {
        FIRST,
        SECOND
    }

    enum OneEnum {
        FIRST
    }

}
