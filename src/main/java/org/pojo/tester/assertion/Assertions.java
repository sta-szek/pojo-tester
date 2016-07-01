package org.pojo.tester.assertion;


import org.pojo.tester.assertion.equals.EqualAssertions;
import org.pojo.tester.assertion.hashcode.HashCodeAssertions;
import org.pojo.tester.assertion.tostring.ToStringAssertions;

public class Assertions {

    public EqualAssertions assertThatEqualsMethod(final Object objectUnderAssert) {
        return new EqualAssertions(objectUnderAssert);
    }

    public HashCodeAssertions assertThatHashCodeMethod(final Object objectUnderAssert) {
        return new HashCodeAssertions(objectUnderAssert);
    }

    public ToStringAssertions assertThatToStringMethod(final Object objectUnderAssert) {
        return new ToStringAssertions(objectUnderAssert);
    }

}
