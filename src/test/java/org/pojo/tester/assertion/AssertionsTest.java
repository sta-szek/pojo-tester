package org.pojo.tester.assertion;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;


public class AssertionsTest {

    @Test
    public void Should_Not_Throw_Exception_If_All_Tests_Passed() {
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
    public void Should_Throw_Exception_If_Any_Test_Failed() {
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
