package org.pojo.tester.field.primitive;


import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class AbstractPrimitiveValueChangerTest {

    @Test
    public void Should_Return_False_When_Field_Is_Not_Primitive() throws Exception {
        // given
        final Field field = Thread.class.getDeclaredField("threadQ");
        final AbstractPrimitiveValueChanger<Object> changerMock = new ImplementationForTest();

        // when
        final boolean result = changerMock.canChange(field);

        // then
        assertThat(result).isFalse();
    }

    private class ImplementationForTest extends AbstractPrimitiveValueChanger<Object> {
        @Override
        protected boolean areDifferent(final Object sourceValue, final Object targetValue) {
            return false;
        }

        @Override
        protected Object increaseValue(final Object value) {
            return null;
        }
    }
}
