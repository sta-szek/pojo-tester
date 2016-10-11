package pl.pojo.tester.internal;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitPlatform.class)
public class GetOrSetValueExceptionTest {

    @Test
    public void Should_Create_Message_Containing_Field_Name_And_Class_And_Cause_Message() {
        // given
        final String causeMessage = "cause-message";
        final String fieldName = "class_a_field_name";
        final Class<A> clazz = A.class;
        final String className = clazz.getName();
        final Exception cause = new Exception(causeMessage);

        // when
        final GetOrSetValueException exception = new GetOrSetValueException(fieldName, clazz, cause);

        // then
        assertThat(exception.getCause()).isEqualTo(cause);
        assertThat(exception.getMessage()).contains(causeMessage)
                                          .contains(fieldName)
                                          .contains(className);
    }

    @Data
    private class A {
        private int class_a_field_name;
    }

}
