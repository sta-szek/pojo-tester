package pl.pojo.tester.internal.assertion.equals;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class OtherTypeEqualsAssertionErrorTest {

    @Test
    public void Should_Return_Expected_Detailed_Message() {
        // given
        final String expectedMessage = "The equals method should return false if object is comparing to object with different type.\n"
                                       + "Current implementation returns true.\n"
                                       + "Object:\n"
                                       + "testedObject\n"
                                       + "should not be equal to:\n"
                                       + "otherObject";
        final Class<String> testedCass = String.class;
        final String testedObject = "testedObject";
        final String otherObject = "otherObject";
        final OtherTypeEqualsAssertionError error = new OtherTypeEqualsAssertionError(testedCass,
                                                                                      testedObject,
                                                                                      otherObject);
        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }
}
