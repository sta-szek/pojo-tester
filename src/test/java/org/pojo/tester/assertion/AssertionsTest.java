package org.pojo.tester.assertion;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;


public class AssertionsTest {

    @Test
    public void shouldNotThrowException_IfAllTestPassed() {
        // given
        final Assertions assertions = new Assertions();
        final ResultBuilder resultBuilder = mock(ResultBuilder.class);
        final Result testResult = mock(Result.class);

        when(resultBuilder.build()).thenReturn(testResult);
        when(testResult.failed()).thenReturn(false);
        setInternalState(assertions, "resultBuilder", resultBuilder);

        // when
        final Throwable result = catchThrowable(assertions::assertAll);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void shouldThrowException_IfAnyTastFailed() {
        // given
        final Assertions assertions = new Assertions();
        final ResultBuilder resultBuilder = mock(ResultBuilder.class);
        final Result testResult = mock(Result.class);

        when(resultBuilder.build()).thenReturn(testResult);
        when(testResult.failed()).thenReturn(true);
        setInternalState(assertions, "resultBuilder", resultBuilder);

        // when
        final Throwable result = catchThrowable(assertions::assertAll);

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }
}