package org.pojo.tester.field;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.internal.WhiteboxImpl.getInternalState;

public class AbstractFieldValueChangerTest {

    private final AbstractFieldValueChanger abstractFieldValueChanger = new ImplementationForTest();

    @Test
    public void Should_Register_First_Value_Changer() {
        // given

        // when
        abstractFieldValueChanger.attachNext(abstractFieldValueChanger);
        final AbstractFieldValueChanger result = getInternalState(abstractFieldValueChanger, "next");

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void Should_Register_Value_Changer_To_Already_Registered_One() {
        // given
        final AbstractFieldValueChanger first = mock(AbstractFieldValueChanger.class, Mockito.CALLS_REAL_METHODS);
        final AbstractFieldValueChanger second = mock(AbstractFieldValueChanger.class, Mockito.CALLS_REAL_METHODS);

        // when
        abstractFieldValueChanger.attachNext(first)
                                 .attachNext(second);

        // then
        verify(first).attachNext(second);
    }

    private class ImplementationForTest extends AbstractFieldValueChanger<Object> {
        @Override
        public boolean areDifferentValues(final Object sourceValue, final Object targetValue) {
            return false;
        }

        @Override
        protected boolean canChange(final Field field) {
            return false;
        }

        @Override
        protected Object increaseValue(final Object value, final Class<?> type) {
            return null;
        }
    }
}
