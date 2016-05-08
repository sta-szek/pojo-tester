package org.pojo.tester.field;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import test.fields.AllFiledTypes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.internal.WhiteboxImpl.getInternalState;

public class AbstractFieldsValuesChangerTest {

    private static AbstractFieldsValuesChanger abstractFieldsValuesChanger;

    @BeforeClass
    public static void beforeClass() {
        abstractFieldsValuesChanger = mock(AbstractFieldsValuesChanger.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void shouldRegisterFirstFieldsValuesChanger() {
        // given

        // when
        abstractFieldsValuesChanger.register(abstractFieldsValuesChanger);
        final AbstractFieldsValuesChanger result = getInternalState(abstractFieldsValuesChanger, "next");

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldRegisterFieldsValuesChangerToNextFieldRegisterValuesChanger() {
        // given
        final AbstractFieldsValuesChanger first = mock(AbstractFieldsValuesChanger.class, Mockito.CALLS_REAL_METHODS);
        final AbstractFieldsValuesChanger second = mock(AbstractFieldsValuesChanger.class, Mockito.CALLS_REAL_METHODS);

        // when
        abstractFieldsValuesChanger.register(first)
                                   .register(second);

        // then
        verify(first).register(second);
    }

    @Test
    public void shouldReturnFalseIfCanNotChange() throws NoSuchFieldException {
        // given

        // when
        final boolean result = abstractFieldsValuesChanger.canChange(AllFiledTypes.class.getDeclaredField("finalIntType"));

        // then
        assertThat(result).isFalse();
    }

}
