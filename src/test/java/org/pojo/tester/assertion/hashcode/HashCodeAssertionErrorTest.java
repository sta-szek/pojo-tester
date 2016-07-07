package org.pojo.tester.assertion.hashcode;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(JUnitPlatform.class)
public class HashCodeAssertionErrorTest {

    @Test
    public void Should_Return_Expected_Error_Prefix() throws NoSuchFieldException {
        // given
        final String expectedMessage = "Class java.lang.String has bad 'hashCode' method implementation.";
        final Class<String> testedCass = String.class;
        final HashCodeAssertionError error = new MockOfEqualsAssertionError(testedCass);

        // when
        final String result = error.getErrorPrefix();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }

    class MockOfEqualsAssertionError extends HashCodeAssertionError {
        MockOfEqualsAssertionError(final Class<?> testedCass) {
            super(testedCass);
        }

        @Override
        protected String getDetailedMessage() {
            return null;
        }
    }
}
