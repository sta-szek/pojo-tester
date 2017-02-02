package pl.pojo.tester.internal.assertion.tostring;

import classesForTest.fields.TestEnum1;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


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

    private class ToStringWithoutField {

        private final int a = 1;
        private final float b = 1.43F;
        private final Object obj = null;
        private TestEnum1 testEnum;

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("a", a)
                                            .append("b", b)
                                            .append("obj", obj)
                                            .toString();
        }
    }
}
