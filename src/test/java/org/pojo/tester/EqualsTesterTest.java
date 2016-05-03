package org.pojo.tester;

import org.junit.Test;
import test.equals.*;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;
import static org.pojo.tester.FieldPredicate.exclude;
import static org.pojo.tester.FieldPredicate.include;

public class EqualsTesterTest {

    @Test
    public void shouldPassAllEqualsTests() {
        // given
        final Class[] classesToTest = {GoodPojo_Equals_HashCode_ToString.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEqualsMethod(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void shouldPassAllEqualsTestsExcludingFields() {
        // given
        final EqualsTester equalsTester = new EqualsTester();
        final Class<?> clazz = BadPojoEqualsDifferentObjectSameType.class;
        final ArrayList<String> excludedFields = newArrayList("notIncludedToEqual_byteField", "notIncludedToEqual_shortType");

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEqualsMethod(clazz, exclude(excludedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void shouldPassAllEqualsTestsIncludingFields() {
        // given
        final EqualsTester equalsTester = new EqualsTester();
        final Class<?> clazz = BadPojoEqualsDifferentObjectSameType.class;
        final ArrayList<String> includedFields = newArrayList("byteField", "shortType");

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEqualsMethod(clazz, include(includedFields)));

        // then
        assertThat(result).isNull();
    }


    @Test
    public void shouldNotPassNullTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsNull.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEqualsMethod(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void shouldNotPassItselfTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsItself.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEqualsMethod(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void shouldNotPassDifferentTypeTest() {
        // given
        final Class[] classesToTest = {BadPojoEqualsDifferentType.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEqualsMethod(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void shouldFailMultipleClasses() {
        // given
        final Class[] classesToTest = {BadPojoEqualsNull.class, BadPojoEqualsDifferentType.class, BadPojoEqualsItself.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEqualsMethod(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void shouldFailDifferentObjectWithSameType() {
        // given
        final Class[] classesToTest = {BadPojoEqualsDifferentObjectSameType.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testEqualsMethod(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

}