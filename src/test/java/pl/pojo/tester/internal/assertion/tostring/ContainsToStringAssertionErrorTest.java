package pl.pojo.tester.internal.assertion.tostring;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class ContainsToStringAssertionErrorTest {

    @Test
    public void Should_Return_Expected_Detailed_Message() throws NoSuchFieldException {
        // given
        final String expectedMessage = "The toString method should contain:\n"
                                       + "value\n"
                                       + "But does not.\n"
                                       + "Result of toString:\n"
                                       + "toString";
        final Class<String> testedCass = String.class;
        final String value = "value";
        final String toString = "toString";
        final ContainsToStringAssertionError error = new ContainsToStringAssertionError(testedCass, value, toString);

        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }
}
