package pl.pojo.tester.internal.assertion.hashcode;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class AbstractHashCodeAssertionErrorTest {

    @Test
    void Should_Return_Expected_Error_Prefix() {
        // given
        final String expectedMessage = "Class java.lang.String has bad 'hashCode' method implementation.";
        final Class<String> testedCass = String.class;
        final AbstractHashCodeAssertionError error = new MockOfEqualsAssertionError(testedCass);

        // when
        final String result = error.getErrorPrefix();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }

    class MockOfEqualsAssertionError extends AbstractHashCodeAssertionError {
        MockOfEqualsAssertionError(final Class<?> testedCass) {
            super(testedCass);
        }

        @Override
        protected String getDetailedMessage() {
            return null;
        }
    }
}
