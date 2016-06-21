package org.pojo.tester.assertion;

import org.junit.jupiter.api.Test;
import test.tostring.ToStringWithoutField;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


public class ToStringAssertionsTest {

    @Test
    public void Should_Fail_When_ToString_Method_Does_Not_Contain_Value() {
        // given
        final ToStringWithoutField objectUnderAssert = new ToStringWithoutField();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final ToStringAssertions toStringAssertions = new ToStringAssertions(resultBuilder, objectUnderAssert);

        // when
        toStringAssertions.contains("unexpectedField", "unexpectedValue");

        // then
        verify(resultBuilder).fail(eq(ToStringWithoutField.class), anyString(), anyString());
    }

    @Test
    public void Should_Fail_When_ToString_Method_Contains_Value() {
        // given
        final ToStringWithoutField objectUnderAssert = new ToStringWithoutField();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final ToStringAssertions toStringAssertions = new ToStringAssertions(resultBuilder, objectUnderAssert);

        // when
        toStringAssertions.doestNotContain("b", "1.43");

        // then
        verify(resultBuilder).fail(eq(ToStringWithoutField.class), anyString(), anyString());
    }


    @Test
    public void Should_Pass_When_ToString_Method_Contains_Value() {
        // given
        final ToStringWithoutField objectUnderAssert = new ToStringWithoutField();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final ToStringAssertions toStringAssertions = new ToStringAssertions(resultBuilder, objectUnderAssert);

        // when
        toStringAssertions.contains("a", "1");

        // then
        verify(resultBuilder).pass(eq(ToStringWithoutField.class), anyString());
    }

    @Test
    public void Should_Pass_When_ToString_Method_Does_Not_Contain_Value() {
        // given
        final ToStringWithoutField objectUnderAssert = new ToStringWithoutField();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final ToStringAssertions toStringAssertions = new ToStringAssertions(resultBuilder, objectUnderAssert);

        // when
        toStringAssertions.doestNotContain("testEnum", "ENUM1");

        // then
        verify(resultBuilder).pass(eq(ToStringWithoutField.class), anyString());
    }


}
