package pl.pojo.tester.internal.assertion.equals;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class EqualEqualsAssertionErrorTest {

    @Test
    public void Should_Return_Expected_Detailed_Message() {
        // given
        final String expectedMessage = "The equals method should return true if objects should be equal.\n"
                                       + "Current implementation returns false.\n"
                                       + "Object:\n"
                                       + "testedObject\n"
                                       + "should be equal to:\n"
                                       + "otherObject";
        final Class<String> testedCass = String.class;
        final String testedObject = "testedObject";
        final String otherObject = "otherObject";
        final EqualEqualsAssertionError error = new EqualEqualsAssertionError(testedCass, testedObject, otherObject);

        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }
}
