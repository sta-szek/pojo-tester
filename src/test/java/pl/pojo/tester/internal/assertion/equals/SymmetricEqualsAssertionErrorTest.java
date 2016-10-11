package pl.pojo.tester.internal.assertion.equals;


import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class SymmetricEqualsAssertionErrorTest {

    @Test
    public void Should_Return_Expected_Detailed_Message() {
        // given
        final String expectedMessage = "The equals method should return true for both a.equals(b) and b.equals(a).\n"
                                       + "Current implementation returns:\n"
                                       + "true for a.equals(b),\n"
                                       + "false for b.equals(a),\n"
                                       + "where 'a' is:\n"
                                       + "testedObject\n"
                                       + "and 'b' is:\n"
                                       + "otherObject";
        final Class<String> testedCass = String.class;
        final String testedObject = "testedObject";
        final String otherObject = "otherObject";
        final boolean firstResult = true;
        final boolean secondResult = false;
        final SymmetricEqualsAssertionError error = new SymmetricEqualsAssertionError(testedCass,
                                                                                      testedObject,
                                                                                      otherObject,
                                                                                      firstResult,
                                                                                      secondResult);
        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }
}
