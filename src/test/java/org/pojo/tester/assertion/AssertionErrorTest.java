package org.pojo.tester.assertion;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AssertionErrorTest {

    @Test
    public void Should_Set_Stack_Trace_To_Empty_Array() {
        // given
        final Class<String> testedCass = String.class;
        final StackTraceElement[] expectedResult = new StackTraceElement[]{};
        final AssertionError error = new MockOfAssertionError(testedCass);

        // when
        final StackTraceElement[] result = error.getStackTrace();

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void Should_Return_Expected_Message() {
        // given
        final Class<String> testedCass = String.class;
        final String expectedResult = "\n"
                                      + "\n"
                                      + "\n"
                                      + "errorPrefix\n"
                                      + "detailedMessage";
        final AssertionError error = new MockOfAssertionError(testedCass);

        // when
        final String result = error.getMessage();

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    class MockOfAssertionError extends AssertionError {

        MockOfAssertionError(final Class<?> testedCass) {
            super(testedCass);
        }

        @Override
        protected String getErrorPrefix() {
            return "errorPrefix";
        }

        @Override
        protected String getDetailedMessage() {
            return "detailedMessage";
        }
    }
}
