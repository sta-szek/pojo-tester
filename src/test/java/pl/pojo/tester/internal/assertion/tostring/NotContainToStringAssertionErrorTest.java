package pl.pojo.tester.internal.assertion.tostring;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class NotContainToStringAssertionErrorTest {

    @Test
    public void Should_Return_Expected_Detailed_Message() throws NoSuchFieldException {
        // given
        final String expectedMessage = "The toString method should not contain:\n"
                                       + "value\n"
                                       + "but does.\n"
                                       + "Result of toString:\n"
                                       + "toString";
        final Class<String> testedCass = String.class;
        final String value = "value";
        final String toString = "toString";
        final NotContainToStringAssertionError error = new NotContainToStringAssertionError(testedCass, value, toString);

        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }
}
