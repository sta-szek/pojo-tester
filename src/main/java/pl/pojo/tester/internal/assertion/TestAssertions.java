package pl.pojo.tester.internal.assertion;


import pl.pojo.tester.internal.assertion.constructor.ConstructorAssertions;
import pl.pojo.tester.internal.assertion.equals.EqualAssertions;
import pl.pojo.tester.internal.assertion.getter.GetterAssertions;
import pl.pojo.tester.internal.assertion.hashcode.HashCodeAssertions;
import pl.pojo.tester.internal.assertion.setter.SetterAssertions;
import pl.pojo.tester.internal.assertion.tostring.ToStringAssertions;

import java.lang.reflect.Constructor;

public class TestAssertions {

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

    public ConstructorAssertions assertThatConstructor(final Constructor<?> constructorUnderAssert) {
        return new ConstructorAssertions(constructorUnderAssert);
    }
}
