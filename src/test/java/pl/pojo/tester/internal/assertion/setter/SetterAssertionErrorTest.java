package pl.pojo.tester.internal.assertion.setter;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;


class SetterAssertionErrorTest {

    @Test
    void Should_Return_Expected_Detailed_Message() throws NoSuchFieldException {
        // given
        final String expectedMessage = "The setter method for field 'private int java.lang.String.hash' should set field value.\n"
                + "Current implementation does not set the value.\n"
                + "Expected value:\n"
                + "expectedValue\n"
                + "but was:\n"
                + "currentValue";
        final Class<String> testedCass = String.class;
        final String currentValue = "currentValue";
        final Field field = testedCass.getDeclaredField("hash");
        final String expectedValue = "expectedValue";
        final SetterAssertionError error = new SetterAssertionError(testedCass, field, expectedValue, currentValue);

        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }

    @Test
    void Should_Return_Expected_Error_Prefix() throws NoSuchFieldException {
        // given
        final String expectedMessage = "Class java.lang.String has bad 'setter' method implementation.";
        final Class<String> testedCass = String.class;
        final String currentValue = "currentValue";
        final Field field = testedCass.getDeclaredField("hash");
        final String expectedValue = "expectedValue";
        final SetterAssertionError error = new SetterAssertionError(testedCass, field, expectedValue, currentValue);

        // when
        final String result = error.getErrorPrefix();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }
}
