package org.pojo.tester;

import org.junit.Test;
import test.utils.BadPojoEqualsDifferentType;
import test.utils.BadPojoEqualsItself;
import test.utils.BadPojoEqualsNull;
import test.utils.GoodPojo_Equals_HashCode_ToString;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class EqualsTesterTest {

    private static final EqualsTester equalsTester = new EqualsTester();

    @Test
    public void shouldPassAllEqualsTests() {
        // given
        final Class[] classesToTest = {GoodPojo_Equals_HashCode_ToString.class};

        // when
        equalsTester.testEquals(classesToTest);

        // then
    }

    @Test
    public void shouldNotPassNullTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsNull.class};

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEquals(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void shouldNotPassItselfTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsItself.class};

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEquals(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void shouldNotPassDifferentTypeTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsDifferentType.class};

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEquals(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void shouldTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsNull.class, BadPojoEqualsDifferentType.class, BadPojoEqualsItself.class};

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEquals(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }
    
}