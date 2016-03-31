package pojo.equals;

import org.junit.Test;
import pojo.equals.test.pojos.BadPojoEqualsDifferentType;
import pojo.equals.test.pojos.BadPojoEqualsItself;
import pojo.equals.test.pojos.BadPojoEqualsNull;
import pojo.equals.test.pojos.GoodPojo_Equals_HashCode_ToString;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualsTesterTest {

    private static final EqualsTester equalsTester = new EqualsTester();

    @Test
    public void shouldPassAllEqualsTests() {
        // given
        final Class[] classesToTest = {GoodPojo_Equals_HashCode_ToString.class};

        // when
        final TestResult testResult = equalsTester.testEquals(classesToTest);

        // then
        assertThat(testResult.getPassedSize()).isEqualTo(classesToTest.length);
    }

    @Test
    public void shouldNotPassNullTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsNull.class};

        // when
        final TestResult testResult = equalsTester.testEquals(classesToTest);

        // then
        assertThat(testResult.getFailedSize()).isEqualTo(classesToTest.length);
    }

    @Test
    public void shouldNotPassItselfTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsItself.class};

        // when
        final TestResult testResult = equalsTester.testEquals(classesToTest);

        // then
        assertThat(testResult.getFailedSize()).isEqualTo(classesToTest.length);
    }

    @Test
    public void shouldNotPassDifferentTypeTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsDifferentType.class};

        // when
        final TestResult testResult = equalsTester.testEquals(classesToTest);

        // then
        assertThat(testResult.getFailedSize()).as(testResult.getFormattedMessage())
                                              .isEqualTo(classesToTest.length);
    }

    @Test
    public void shouldTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsNull.class,
                                       BadPojoEqualsDifferentType.class,
                                       BadPojoEqualsItself.class};

        // when
        final TestResult testResult = equalsTester.testEquals(classesToTest);

        // then
        try {
            assertThat(testResult.getPassedSize())
                    .isEqualTo(classesToTest.length);
        } catch (final AssertionError e) {
            System.err.println(testResult.getFormattedMessage());
        }
    }


}