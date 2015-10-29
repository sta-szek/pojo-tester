package pojo.equals.field;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import pojo.equals.test.pojos.TestHelper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.internal.WhiteboxImpl.getInternalState;

public class FieldsValuesChangerTest {

    static FieldsValuesChanger fieldsValuesChanger;

    @BeforeClass
    public static void beforeClass() {
        fieldsValuesChanger = Mockito.mock(FieldsValuesChanger.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void shouldRegisterFirstFieldsValuesChanger() {
        // given

        // when
        fieldsValuesChanger.register(fieldsValuesChanger);
        FieldsValuesChanger result = getInternalState(fieldsValuesChanger, "fieldsValuesChanger");

        // then
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldRegisterFieldsValuesChangerToNextFieldRegisterValuesChanger() {
        // given
        FieldsValuesChanger first = Mockito.mock(FieldsValuesChanger.class, Mockito.CALLS_REAL_METHODS);
        FieldsValuesChanger second = Mockito.mock(FieldsValuesChanger.class, Mockito.CALLS_REAL_METHODS);

        // when
        fieldsValuesChanger.register(first)
                           .register(second);

        // then
        verify(first).register(second);
    }

    @Test
    public void shouldReturnTrueIfCanChange() throws NoSuchFieldException {
        // given

        // when
        boolean result = fieldsValuesChanger.canChange(TestHelper.class.getDeclaredField("intType"));

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseIfCanNotChange() throws NoSuchFieldException {
        // given

        // when
        boolean result = fieldsValuesChanger.canChange(TestHelper.class.getDeclaredField("finalIntType"));

        // then
        assertThat(result).isFalse();
    }

}