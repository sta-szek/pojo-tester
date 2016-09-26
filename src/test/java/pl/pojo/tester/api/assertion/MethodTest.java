package pl.pojo.tester.api.assertion;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.api.AbstractTester;
import pl.pojo.tester.api.EqualsTester;
import pl.pojo.tester.api.GetterTester;
import pl.pojo.tester.api.HashCodeTester;
import pl.pojo.tester.api.SetterTester;
import pl.pojo.tester.api.ToStringTester;

import static helpers.TestHelper.getDefaultDisplayName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@RunWith(JUnitPlatform.class)
public class MethodTest {

    @TestFactory
    public Stream<DynamicTest> Should_Return_Expected_Tester() {
        return Stream.of(new TestCase(Method.EQUALS, EqualsTester.class),
                         new TestCase(Method.HASH_CODE, HashCodeTester.class),
                         new TestCase(Method.TO_STRING, ToStringTester.class),
                         new TestCase(Method.SETTER, SetterTester.class),
                         new TestCase(Method.GETTER, GetterTester.class))
                     .map(value -> dynamicTest(getDefaultDisplayName(value), Should_Return_Expected_Tester(value)));
    }

    private Executable Should_Return_Expected_Tester(final TestCase testCase) {
        return () -> {
            // when
            final Class<? extends AbstractTester> result = testCase.method.getTester()
                                                                          .getClass();

            // then
            assertThat(result).isEqualTo(testCase.expectedTester);
        };
    }

    @Data
    @AllArgsConstructor
    private class TestCase {
        private Method method;
        private Class<? extends AbstractTester> expectedTester;
    }
}
