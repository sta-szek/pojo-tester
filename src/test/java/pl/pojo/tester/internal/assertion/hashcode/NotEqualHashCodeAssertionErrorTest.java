package pl.pojo.tester.internal.assertion.hashcode;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class NotEqualHashCodeAssertionErrorTest {

    @Test
    public void Should_Return_Expected_Detailed_Message() {
        // given
        final String expectedMessage = "The hashCode method should return different hash codes for non equal objects.\n"
                                       + "Current implementation returns same hash codes.\n"
                                       + "Object:\n"
                                       + "testedObject\n"
                                       + "and\n"
                                       + "otherObject\n"
                                       + "should have different hash codes:\n"
                                       + "1\n"
                                       + "and\n"
                                       + "2";
        final Class<String> testedCass = String.class;
        final String testedObject = "testedObject";
        final String otherObject = "otherObject";
        final int secondHashCode = 2;
        final int firstHashCode = 1;
        final NotEqualHashCodeAssertionError error = new NotEqualHashCodeAssertionError(testedCass,
                                                                                        testedObject,
                                                                                        otherObject,
                                                                                        firstHashCode,
                                                                                        secondHashCode);
        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }

}
