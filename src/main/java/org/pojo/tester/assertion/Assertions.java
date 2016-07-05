package org.pojo.tester.assertion;


import org.pojo.tester.assertion.equals.EqualAssertions;
import org.pojo.tester.assertion.getter.GetterAssertions;
import org.pojo.tester.assertion.hashcode.HashCodeAssertions;
import org.pojo.tester.assertion.setter.SetterAssertions;
import org.pojo.tester.assertion.tostring.ToStringAssertions;

public class Assertions {

    public EqualAssertions assertThatEqualsMethodFor(final Object objectUnderAssert) {
        return new EqualAssertions(objectUnderAssert);
    }

    public HashCodeAssertions assertThatHashCodeMethodFor(final Object objectUnderAssert) {
        return new HashCodeAssertions(objectUnderAssert);
    }

    public ToStringAssertions assertThatToStringMethodFor(final Object objectUnderAssert) {
        return new ToStringAssertions(objectUnderAssert);
    }

    public SetterAssertions assertThatSetMethodFor(final Object objectUnderAssert) {
        return new SetterAssertions(objectUnderAssert);
    }

    public GetterAssertions assertThatGetMethodFor(final Object objectUnderAssert) {
        return new GetterAssertions(objectUnderAssert);
    }
}
