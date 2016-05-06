package org.pojo.tester.field.primitive;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.pojo.tester.field.AbstractFieldsValuesChanger;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import test.fields.AllFiledTypes;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AbstractPrimitiveValueChanger.class)
public class AbstractPrimitiveValueChangerTest {

    @Test
    public void shouldCreateInstanceWithPrimitiveChangers() throws InstantiationException, IllegalAccessException {
        // given

        // when
        final AbstractFieldsValuesChanger abstractFieldsValuesChanger = AbstractPrimitiveValueChanger.getInstance();

        // then
        assertThat(abstractFieldsValuesChanger).isNotNull();
    }


    @Test
    public void shouldReturnFalseWhenGenericTypeIsNotPrimitive() throws Exception {
        // given
        final Field field = AllFiledTypes.class.getDeclaredField("intType");

        final AbstractPrimitiveValueChanger<Object> changerMock = mock(AbstractPrimitiveValueChanger.class, CALLS_REAL_METHODS);
        doReturn(Object.class).when(changerMock, "getGenericTypeClass");
        doReturn(true).when(changerMock, "isPrimitive", field);

        // when
        final boolean result = changerMock.canChange(field);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnFalseWhenFieldIsNotPrimitive() throws Exception {
        // given
        final Field field = Thread.class.getDeclaredField("threadQ");

        final AbstractPrimitiveValueChanger<Object> changerMock = mock(AbstractPrimitiveValueChanger.class, CALLS_REAL_METHODS);
        doReturn(Integer.class).when(changerMock, "getGenericTypeClass");

        // when
        final boolean result = changerMock.canChange(field);

        // then
        assertThat(result).isFalse();
    }

}
