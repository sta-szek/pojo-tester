package pl.pojo.tester.internal.instantiator;


import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import test.instantiator.enums.DoubleEnum;
import test.instantiator.enums.EmptyEnum;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class EnumInstantiatorTest {

    @Test
    public void Should_Return_Null_When_Enum_Is_Empty() {
        // given
        final EnumInstantiator instantiator = new EnumInstantiator(EmptyEnum.class);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Return_Any_Enum_Value() {
        // given
        final Class<DoubleEnum> doubleEnumClass = DoubleEnum.class;
        final EnumInstantiator instantiator = new EnumInstantiator(doubleEnumClass);

        // when
        final Object result = instantiator.instantiate();

        // then
        assertThat(result).isInstanceOf(doubleEnumClass);
    }

}
