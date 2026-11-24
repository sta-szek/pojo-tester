package pl.pojo.tester.internal.field.primitive;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NullBoxedPrimitiveTest {

    @Test
    void Should_Replace_Null_Byte_With_One() {
        assertThat(new ByteValueChanger().increaseValue(null, Byte.class)).isEqualTo((byte) 1);
    }

    @Test
    void Should_Replace_Null_Short_With_One() {
        assertThat(new ShortValueChanger().increaseValue(null, Short.class)).isEqualTo((short) 1);
    }

    @Test
    void Should_Replace_Null_Integer_With_One() {
        assertThat(new IntegerValueChanger().increaseValue(null, Integer.class)).isEqualTo(1);
    }

    @Test
    void Should_Replace_Null_Long_With_One() {
        assertThat(new LongValueChanger().increaseValue(null, Long.class)).isEqualTo(1L);
    }

    @Test
    void Should_Replace_Null_Float_With_One() {
        assertThat(new FloatValueChanger().increaseValue(null, Float.class)).isEqualTo(1f);
    }

    @Test
    void Should_Replace_Null_Double_With_One() {
        assertThat(new DoubleValueChanger().increaseValue(null, Double.class)).isEqualTo(1d);
    }

    @Test
    void Should_Replace_Null_Character_With_A() {
        assertThat(new CharacterValueChanger().increaseValue(null, Character.class)).isEqualTo('a');
    }

    @Test
    void Should_Replace_Null_Boolean_With_True() {
        assertThat(new BooleanValueChanger().increaseValue(null, Boolean.class)).isTrue();
    }
}
