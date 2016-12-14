package pl.pojo.tester.internal.assertion.equals;


import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


public class NullEqualsAssertionErrorTest {

    @Test
    public void Should_Return_Expected_Detailed_Message() {
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
