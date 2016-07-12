package org.pojo.tester.assertion;

import org.junit.jupiter.api.Test;
import org.pojo.tester.assertion.equals.EqualAssertions;
import org.pojo.tester.assertion.getter.GetterAssertions;
import org.pojo.tester.assertion.hashcode.HashCodeAssertions;
import org.pojo.tester.assertion.setter.SetterAssertions;
import org.pojo.tester.assertion.tostring.ToStringAssertions;

import static org.assertj.core.api.Assertions.assertThat;


public class AssertionsTest {

    @Test
    public void Should_Return_Expected_Equal_Assertion() {
        // given
        final Assertions assertions = new Assertions();
        final String objectUnderTest = "objectUnderTest";
        final EqualAssertions expectedResult = new EqualAssertions(objectUnderTest);

        // when
        final EqualAssertions result = assertions.assertThatEqualsMethodFor(objectUnderTest);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    public void Should_Return_Expected_HashCode_Assertion() {
        // given
        final Assertions assertions = new Assertions();
        final String objectUnderTest = "objectUnderTest";
        final HashCodeAssertions expectedResult = new HashCodeAssertions(objectUnderTest);

        // when
        final HashCodeAssertions result = assertions.assertThatHashCodeMethodFor(objectUnderTest);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    public void Should_Return_Expected_ToString_Assertion() {
        // given
        final Assertions assertions = new Assertions();
        final String objectUnderTest = "objectUnderTest";
        final ToStringAssertions expectedResult = new ToStringAssertions(objectUnderTest);

        // when
        final ToStringAssertions result = assertions.assertThatToStringMethodFor(objectUnderTest);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    public void Should_Return_Expected_Setter_Assertion() {
        // given
        final Assertions assertions = new Assertions();
        final String objectUnderTest = "objectUnderTest";
        final SetterAssertions expectedResult = new SetterAssertions(objectUnderTest);

        // when
        final SetterAssertions result = assertions.assertThatSetMethodFor(objectUnderTest);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    public void Should_Return_Expected_Getter_Assertion() {
        // given
        final Assertions assertions = new Assertions();
        final String objectUnderTest = "objectUnderTest";
        final GetterAssertions expectedResult = new GetterAssertions(objectUnderTest);

        // when
        final GetterAssertions result = assertions.assertThatGetMethodFor(objectUnderTest);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

}
