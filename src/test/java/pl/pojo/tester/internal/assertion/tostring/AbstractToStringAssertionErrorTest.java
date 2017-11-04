package pl.pojo.tester.internal.assertion.tostring;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class AbstractToStringAssertionErrorTest {

    @Test
    void Should_Return_Expected_Error_Prefix() {
        // given
        final String expectedMessage = "Class java.lang.String has bad 'toString' method implementation.";
        final Class<String> testedCass = String.class;
        final AbstractToStringAssertionError error = new MockOfToStringAssertionError(testedCass);

        // when
        final String result = error.getErrorPrefix();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }

    class MockOfToStringAssertionError extends AbstractToStringAssertionError {
        MockOfToStringAssertionError(final Class<?> testedCass) {
            super(testedCass);
        }

        @Override
        protected String getDetailedMessage() {
            return null;
        }
    }
}
