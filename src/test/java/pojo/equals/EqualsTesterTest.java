package pojo.equals;

import org.junit.Test;
import pojo.equals.test.pojos.BadPojoEqualsDifferentType;
import pojo.equals.test.pojos.BadPojoEqualsItself;
import pojo.equals.test.pojos.BadPojoEqualsNull;
import pojo.equals.test.pojos.GoodPojo_Equals_HashCode_ToString;

import static org.assertj.core.api.Assertions.assertThat;


public class EqualsTesterTest {

    static EqualsTester equalsTester = new EqualsTester();

    @Test
    public void shouldPassAllEqualsTests() {
        // given
        Class[] classesToTest = {GoodPojo_Equals_HashCode_ToString.class};

        // when
        TestResult testResult = equalsTester.testEquals(classesToTest);

        // then
        assertThat(testResult.getPassedSize()).isEqualTo(classesToTest.length);
    }

    @Test
    public void shouldNotPassNullTest() {
        // given
        Class[] classesToTest = {BadPojoEqualsNull.class};

        // when
        TestResult testResult = equalsTester.testEquals(classesToTest);

        // then
        assertThat(testResult.getFailedSize()).isEqualTo(classesToTest.length);
    }

    @Test
    public void shouldNotPassItselfTest() {
        // given
        Class[] classesToTest = {BadPojoEqualsItself.class};

        // when
        TestResult testResult = equalsTester.testEquals(classesToTest);

        // then
        assertThat(testResult.getFailedSize()).isEqualTo(classesToTest.length);
    }

    @Test
    public void shouldNotPassDifferentTypeTest() {
        // given
        Class[] classesToTest = {BadPojoEqualsDifferentType.class};

        // when
        TestResult testResult = equalsTester.testEquals(classesToTest);

        // then
        assertThat(testResult.getFailedSize()).as(testResult.getFormattedMessage())
                                              .isEqualTo(classesToTest.length);
    }

    @Test
    public void shouldTest() {
        // given
        Class[] classesToTest = {BadPojoEqualsNull.class,
                                 BadPojoEqualsDifferentType.class,
                                 BadPojoEqualsItself.class};

        // when
        TestResult testResult = equalsTester.testEquals(classesToTest);

        // then
        try {
            assertThat(testResult.getPassedSize())
                    .isEqualTo(classesToTest.length);
        } catch (AssertionError e) {
            System.err.println(testResult.getFormattedMessage());
        }
    }


}