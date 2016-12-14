package pl.pojo.tester.internal.assertion.hashcode;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


public class ConsistentHashCodeAssertionErrorTest {

    @Test
    public void Should_Return_Expected_Detailed_Message() {
        // given
        final String expectedMessage = "The hashCode method should return same hash code for same object.\n"
                                       + "Current implementation returns different values.\n"
                                       + "Object:\n"
                                       + "testedObject\n"
                                       + "has two different hash codes:\n"
                                       + "1\n"
                                       + "and\n"
                                       + "2";
        final Class<String> testedCass = String.class;
        final String testedObject = "testedObject";
        final int secondHashCode = 2;
        final int firstHashCode = 1;
        final ConsistentHashCodeAssertionError error = new ConsistentHashCodeAssertionError(testedCass,
                                                                                            testedObject,
                                                                                            firstHashCode,
                                                                                            secondHashCode);
        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }

}
