package org.pojo.tester;

import org.junit.Test;
import test.equals.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class EqualsTesterTest {

    @Test
    public void shouldPassAllEqualsTests() {
        // given
        final Class[] classesToTest = {GoodPojo_Equals_HashCode_ToString.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEquals(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void shouldNotPassNullTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsNull.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEquals(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void shouldNotPassItselfTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsItself.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEquals(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void shouldNotPassDifferentTypeTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsDifferentType.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEquals(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void shouldFailMultipleClasses() {
        // given
        final Class[] classesToTest = {BadPojoEqualsNull.class, BadPojoEqualsDifferentType.class, BadPojoEqualsItself.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEquals(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void shouldFailDifferentObjectWithSameType() {
        // given
        final Class[] classesToTest = {BadPojoEqualsDifferentObjectSameType.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEquals(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

}