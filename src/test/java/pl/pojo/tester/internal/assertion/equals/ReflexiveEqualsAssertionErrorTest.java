package pl.pojo.tester.internal.assertion.equals;


import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class ReflexiveEqualsAssertionErrorTest {

    @Test
    public void Should_Return_Expected_Detailed_Message() {
        // given
        final String expectedMessage = "The equals method should return true if object is comparing to itself.\n"
                                       + "Current implementation returns false.\n"
                                       + "Object:\n"
                                       + "testedObject\n"
                                       + "should be equal to:\n"
                                       + "testedObject";
        final Class<String> testedCass = String.class;
        final String testedObject = "testedObject";
        final ReflexiveEqualsAssertionError error = new ReflexiveEqualsAssertionError(testedCass, testedObject);

        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }
}
