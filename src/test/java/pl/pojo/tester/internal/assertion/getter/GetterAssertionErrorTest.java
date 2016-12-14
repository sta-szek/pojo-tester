package pl.pojo.tester.internal.assertion.getter;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


public class GetterAssertionErrorTest {

    @Test
    public void Should_Return_Expected_Detailed_Message() throws NoSuchFieldException {
        // given
        final String expectedMessage = "The getter method for field 'private int java.lang.String.hash' should return field value.\n"
                                       + "Current implementation returns different value.\n"
                                       + "Expected value:\n"
                                       + "expectedValue\n"
                                       + "but was:\n"
                                       + "currentValue";
        final Class<String> testedCass = String.class;
        final String currentValue = "currentValue";
        final Field field = testedCass.getDeclaredField("hash");
        final String expectedValue = "expectedValue";
        final GetterAssertionError error = new GetterAssertionError(testedCass, field, expectedValue, currentValue);

        // when
        final String result = error.getDetailedMessage();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }

    @Test
    public void Should_Return_Expected_Error_Prefix() throws NoSuchFieldException {
        // given
        final String expectedMessage = "Class java.lang.String has bad 'getter' method implementation.";
        final Class<String> testedCass = String.class;
        final String currentValue = "currentValue";
        final Field field = testedCass.getDeclaredField("hash");
        final String expectedValue = "expectedValue";
        final GetterAssertionError error = new GetterAssertionError(testedCass, field, expectedValue, currentValue);

        // when
        final String result = error.getErrorPrefix();

        // then
        assertThat(result).isEqualTo(expectedMessage);
    }
}
