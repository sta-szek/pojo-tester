package org.pojo.tester.assertion.tostring;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import test.tostring.ToStringWithoutField;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(JUnitPlatform.class)
public class ToStringAssertionsTest {

    @Test
    public void Should_Throw_Exception_When_ToString_Method_Does_Not_Contain_Value() {
        // given
        final ToStringWithoutField objectUnderAssert = new ToStringWithoutField();
        final ToStringAssertions toStringAssertions = new ToStringAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> toStringAssertions.contains("unexpectedField", "unexpectedValue"));

        // then
        assertThat(result).isInstanceOf(ContainsToStringAssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_ToString_Method_Contains_Value() {
        // given
        final ToStringWithoutField objectUnderAssert = new ToStringWithoutField();
        final ToStringAssertions toStringAssertions = new ToStringAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> toStringAssertions.doestNotContain("b", "1.43"));

        // then
        assertThat(result).isInstanceOf(NotContainToStringAssertionError.class);
    }


    @Test
    public void Should_Not_Throw_Exception_When_ToString_Method_Contains_Value() {
        // given
        final ToStringWithoutField objectUnderAssert = new ToStringWithoutField();
        final ToStringAssertions toStringAssertions = new ToStringAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> toStringAssertions.contains("a", "1"));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Not_Throw_Exception_When_ToString_Method_Does_Not_Contain_Value() {
        // given
        final ToStringWithoutField objectUnderAssert = new ToStringWithoutField();
        final ToStringAssertions toStringAssertions = new ToStringAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> toStringAssertions.doestNotContain("testEnum", "ENUM1"));

        // then
        assertThat(result).isNull();
    }


}
