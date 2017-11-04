package pl.pojo.tester.internal.assertion.equals;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ConsistentEqualsAssertionErrorTest {

    @Test
    void Should_Return_Expected_Detailed_Message() {
        // given
        final String expectedMessage = "The equals method should be consistent when comparing same objects multiple times.\n"
                + "Current implementation returns different results.\n"
                + "When comparing object:\n"
                + "testedObject\n"
                + "to itself, first result was 'true' and second time was 'false'.";
        final Class<String> testedCass = String.class;
        final String testedObject = "testedObject";
        final boolean firstResult = true;
        final boolean secondResult = false;
        final ConsistentEqualsAssertionError error = new ConsistentEqualsAssertionError(testedCass,
                                                                                        testedObject,
                                                                                        firstResult,
                                                                                        secondResult);
        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }
}
