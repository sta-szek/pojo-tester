package pl.pojo.tester.internal.assertion;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.internal.assertion.constructor.ConstructorAssertions;
import pl.pojo.tester.internal.assertion.equals.EqualAssertions;
import pl.pojo.tester.internal.assertion.getter.GetterAssertions;
import pl.pojo.tester.internal.assertion.hashcode.HashCodeAssertions;
import pl.pojo.tester.internal.assertion.setter.SetterAssertions;
import pl.pojo.tester.internal.assertion.tostring.ToStringAssertions;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;


public class TestAssertionsTest {

    @Test
    public void Should_Return_Expected_Equal_Assertion() {
        // given
        final TestAssertions testAssertions = new TestAssertions();
        final String objectUnderTest = "objectUnderTest";
        final EqualAssertions expectedResult = new EqualAssertions(objectUnderTest);

        // when
        final EqualAssertions result = testAssertions.assertThatEqualsMethodFor(objectUnderTest);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    public void Should_Return_Expected_HashCode_Assertion() {
        // given
        final TestAssertions testAssertions = new TestAssertions();
        final String objectUnderTest = "objectUnderTest";
        final HashCodeAssertions expectedResult = new HashCodeAssertions(objectUnderTest);

        // when
        final HashCodeAssertions result = testAssertions.assertThatHashCodeMethodFor(objectUnderTest);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    public void Should_Return_Expected_ToString_Assertion() {
        // given
        final TestAssertions testAssertions = new TestAssertions();
        final String objectUnderTest = "objectUnderTest";
        final ToStringAssertions expectedResult = new ToStringAssertions(objectUnderTest);

        // when
        final ToStringAssertions result = testAssertions.assertThatToStringMethodFor(objectUnderTest);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    public void Should_Return_Expected_Setter_Assertion() {
        // given
        final TestAssertions testAssertions = new TestAssertions();
        final String objectUnderTest = "objectUnderTest";
        final SetterAssertions expectedResult = new SetterAssertions(objectUnderTest);

        // when
        final SetterAssertions result = testAssertions.assertThatSetMethodFor(objectUnderTest);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    public void Should_Return_Expected_Getter_Assertion() {
        // given
        final TestAssertions testAssertions = new TestAssertions();
        final String objectUnderTest = "objectUnderTest";
        final GetterAssertions expectedResult = new GetterAssertions(objectUnderTest);

        // when
        final GetterAssertions result = testAssertions.assertThatGetMethodFor(objectUnderTest);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    public void Should_Return_Expected_Constructor_Assertion() throws NoSuchMethodException {
        // given
        final TestAssertions testAssertions = new TestAssertions();
        final Constructor<Pojo> declaredConstructor = Pojo.class.getDeclaredConstructor();
        final ConstructorAssertions expectedResult = new ConstructorAssertions(declaredConstructor);

        // when
        final ConstructorAssertions result = testAssertions.assertThatConstructor(declaredConstructor);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    private static class Pojo {}

}
