package pl.pojo.tester.internal.assertion.equals;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class EqualsAssertionErrorTest {

    @Test
    void Should_Return_Expected_Error_Prefix() {
        // given
        final String expectedMessage = "Class java.lang.String has bad 'equals' method implementation.";
        final AbstractEqualsAssertionError error = new MockOfEqualsAssertionError(String.class);

        // when
        final String result = error.getErrorPrefix();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }

    class MockOfEqualsAssertionError extends AbstractEqualsAssertionError {

        MockOfEqualsAssertionError(final Class<?> testedCass) {
            super(testedCass);
        }

        @Override
        protected String getDetailedMessage() {
            return null;
        }
    }
}
