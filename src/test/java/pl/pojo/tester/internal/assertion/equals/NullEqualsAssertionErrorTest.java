package pl.pojo.tester.internal.assertion.equals;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class NullEqualsAssertionErrorTest {

    @Test
    void Should_Return_Expected_Detailed_Message() {
        // given
        final String expectedMessage = "The equals method should return false if object is comparing to null.\n"
                + "Current implementation returns true.";
        final Class<String> testedCass = String.class;
        final NullEqualsAssertionError error = new NullEqualsAssertionError(testedCass);

        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }
}
