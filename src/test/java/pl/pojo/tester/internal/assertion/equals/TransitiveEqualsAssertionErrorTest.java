package pl.pojo.tester.internal.assertion.equals;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class TransitiveEqualsAssertionErrorTest {

    @Test
    void Should_Return_Expected_Detailed_Message() {
        // given
        final String expectedMessage = "The equals method should return true in all cases: a.equals(b), b.equals(c) and a.equals(c).\n"
                + "Current implementation returns:\n"
                + "true for a.equals(b),\n"
                + "false for b.equals(c),\n"
                + "true for a.equals(c),\n"
                + "where 'a' is:\n"
                + "a\n"
                + "and 'b' is:\n"
                + "b\n"
                + "and 'c' is:\n"
                + "c";
        final Class<String> testedCass = String.class;
        final String a = "a";
        final String b = "b";
        final String c = "c";
        final boolean firstResult = true;
        final boolean secondResult = false;
        final boolean thirdResult = true;

        final TransitiveEqualsAssertionError error = new TransitiveEqualsAssertionError(testedCass,
                                                                                        a,
                                                                                        b,
                                                                                        c,
                                                                                        firstResult,
                                                                                        secondResult,
                                                                                        thirdResult);
        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }
}
