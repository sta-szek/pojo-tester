package org.pojo.tester.field;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.internal.WhiteboxImpl.getInternalState;

@RunWith(MockitoJUnitRunner.class)
public class AbstractFieldsValuesChangerTest {

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private static AbstractFieldsValuesChanger abstractFieldsValuesChanger;

    @Test
    public void Should_Register_First_Value_Changer() {
        // given

        // when
        abstractFieldsValuesChanger.register(abstractFieldsValuesChanger);
        final AbstractFieldsValuesChanger result = getInternalState(abstractFieldsValuesChanger, "next");

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void Should_Register_Value_Changer_To_Already_Registered_One() {
        // given
        final AbstractFieldsValuesChanger first = mock(AbstractFieldsValuesChanger.class, Mockito.CALLS_REAL_METHODS);
        final AbstractFieldsValuesChanger second = mock(AbstractFieldsValuesChanger.class, Mockito.CALLS_REAL_METHODS);

        // when
        abstractFieldsValuesChanger.register(first)
                                   .register(second);

        // then
        verify(first).register(second);
    }

}
