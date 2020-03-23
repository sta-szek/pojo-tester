package pl.pojo.tester.internal.field.primitive;


import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;


class AbstractPrimitiveValueChangerTest {

    @Test
    void Should_Return_False_When_Field_Is_Not_Primitive() throws Exception {
        // given
        final Field field = Thread.class.getDeclaredField("name");
        final AbstractPrimitiveValueChanger<Object> changerMock = new ImplementationForTest();

        // when
        final boolean result = changerMock.canChange(field.getType());

        // then
        assertThat(result).isFalse();
    }

    private class ImplementationForTest extends AbstractPrimitiveValueChanger<Object> {

        @Override
        protected Object increase(final Object value) {
            return null;
        }
    }
}
