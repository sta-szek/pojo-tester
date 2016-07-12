package org.pojo.tester.assertion.equals;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class EqualsAssertionErrorTest {

    @Test
    public void Should_Return_Expected_Error_Prefix() {
        // given
        final String expectedMessage = "Class java.lang.String has bad 'equals' method implementation.";
        final EqualsAssertionError error = new MockOfEqualsAssertionError(String.class);

        // when
        final String result = error.getErrorPrefix();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }

    class MockOfEqualsAssertionError extends EqualsAssertionError {

        MockOfEqualsAssertionError(final Class<?> testedCass) {
            super(testedCass);
        }

        @Override
        protected String getDetailedMessage() {
            return null;
        }
    }
}
