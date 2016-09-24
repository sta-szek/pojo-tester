package pl.pojo.tester.internal.field;

import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.reflect.Whitebox.getInternalState;

@RunWith(JUnitPlatform.class)
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

    @Test
    public void Should_Not_Change_If_Values_Are_Different() throws NoSuchFieldException {
        // given
        final AbstractFieldValueChanger valueChanger = mock(AbstractFieldValueChanger.class, Mockito.CALLS_REAL_METHODS);
        when(valueChanger.canChange(any())).thenReturn(true);
        when(valueChanger.areDifferentValues(any(), any())).thenReturn(true);

        final int expectedResult = 2;
        final ClassWithSingleIntField sourceObject = new ClassWithSingleIntField(1);
        final ClassWithSingleIntField targetObject = new ClassWithSingleIntField(expectedResult);
        final ArrayList<Field> fields = Lists.newArrayList(sourceObject.getClass()
                                                                       .getDeclaredField("i"));

        // when
        valueChanger.changeFieldsValues(sourceObject, targetObject, fields);

        // then
        assertThat(targetObject.getI()).isEqualTo(expectedResult);
    }

    private class ImplementationForTest extends AbstractFieldValueChanger<Object> {
        @Override
        public boolean areDifferentValues(final Object sourceValue, final Object targetValue) {
            return false;
        }

        @Override
        protected boolean canChange(final Class<?> type) {
            return false;
        }

        @Override
        protected Object increaseValue(final Object value, final Class<?> type) {
            return null;
        }
    }

    @Data
    @AllArgsConstructor
    private class ClassWithSingleIntField {
        private int i;
    }
}
