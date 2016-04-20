package org.pojo.tester.field;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import test.fields.AllFiledTypes;

import java.lang.NoSuchFieldException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.internal.WhiteboxImpl.getInternalState;

public class FieldsValuesChangerTest {

    private static FieldsValuesChanger fieldsValuesChanger;

    @BeforeClass
    public static void beforeClass() {
        fieldsValuesChanger = mock(FieldsValuesChanger.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void shouldRegisterFirstFieldsValuesChanger() {
        // given

        // when
        fieldsValuesChanger.register(fieldsValuesChanger);
        final FieldsValuesChanger result = getInternalState(fieldsValuesChanger, "fieldsValuesChanger");

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldRegisterFieldsValuesChangerToNextFieldRegisterValuesChanger() {
        // given
        final FieldsValuesChanger first = mock(FieldsValuesChanger.class, Mockito.CALLS_REAL_METHODS);
        final FieldsValuesChanger second = mock(FieldsValuesChanger.class, Mockito.CALLS_REAL_METHODS);

        // when
        fieldsValuesChanger.register(first)
                           .register(second);

        // then
        verify(first).register(second);
    }

    @Test
    public void shouldReturnTrueIfCanChange() throws java.lang.NoSuchFieldException {
        // given

        // when
        final boolean result = fieldsValuesChanger.canChange(AllFiledTypes.class.getDeclaredField("intType"));

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseIfCanNotChange() throws NoSuchFieldException {
        // given

        // when
        final boolean result = fieldsValuesChanger.canChange(AllFiledTypes.class.getDeclaredField("finalIntType"));

        // then
        assertThat(result).isFalse();
    }

}