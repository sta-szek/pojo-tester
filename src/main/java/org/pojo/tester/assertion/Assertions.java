package org.pojo.tester.assertion;


public class Assertions {

    private final ResultBuilder resultBuilder = new ResultBuilder();

    public EqualAssertions assertThatEqualsMethod(final Object objectUnderAssert) {
        return new EqualAssertions(resultBuilder, objectUnderAssert);
    }

    public HashCodeAssertions assertThatHashCodeMethod(final Object objectUnderAssert) {
        return new HashCodeAssertions(resultBuilder, objectUnderAssert);
    }

    public ToStringAssertions assertThatToStringMethod(final Object objectUnderAssert) {
        return new ToStringAssertions(resultBuilder, objectUnderAssert);
    }

    public void assertAll() {
        final Result result = resultBuilder.build();
        if (result.failed()) {
            throw new AssertionError(result.getMessage());
        }
    }
}
