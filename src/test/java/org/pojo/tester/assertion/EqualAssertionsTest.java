package org.pojo.tester.assertion;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.pojo.tester.assertion.equals.EqualAssertions;
import test.GoodPojo_Equals_HashCode_ToString;
import test.equals.BadPojoEqualsDifferentType;
import test.equals.BadPojoEqualsNull;
import test.equals.BadPojoEqualsWithEqualObject;
import test.equals.BadPojoEquals_NotConsistent;
import test.equals.BadPojoEquals_NotSymmetric;
import test.equals.BadPojoEquals_NotTransitive_A_B;
import test.equals.BadPojoEquals_NotTransitive_B_C;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(JUnitPlatform.class)
public class EqualAssertionsTest {

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Is_Not_Reflexive() {
        // given
        final BadPojoEqualsNull objectUnderAssert = new BadPojoEqualsNull();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isReflexive());

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Is_Not_Symmetric() {
        // given
        final BadPojoEquals_NotSymmetric objectUnderAssert = new BadPojoEquals_NotSymmetric();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isSymmetric(objectUnderAssert));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Is_Not_Consistent() {
        // given
        final BadPojoEquals_NotConsistent objectUnderAssert = new BadPojoEquals_NotConsistent();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isConsistent());

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Is_Not_Transitive_Between_A_And_B() {
        // given
        final BadPojoEquals_NotTransitive_A_B objectUnderAssert = new BadPojoEquals_NotTransitive_A_B();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isTransitive(objectUnderAssert, objectUnderAssert));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Is_Not_Transitive_Between_B_And_C() {
        // given
        final BadPojoEquals_NotTransitive_B_C objectUnderAssert = new BadPojoEquals_NotTransitive_B_C();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isTransitive(objectUnderAssert, objectUnderAssert));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Return_True_Compared_To_Null() {
        // given
        final BadPojoEqualsNull objectUnderAssert = new BadPojoEqualsNull();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isNotEqualToNull());

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Return_True_Compared_To_Different_Type() {
        // given
        final BadPojoEqualsDifferentType objectUnderAssert = new BadPojoEqualsDifferentType();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isNotEqualToObjectWithDifferentType(this));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Return_True_Compared_To_Not_Equal_Object() {
        // given
        final BadPojoEqualsWithEqualObject objectUnderAssert = new BadPojoEqualsWithEqualObject();
        final BadPojoEqualsWithEqualObject otherObject = new BadPojoEqualsWithEqualObject();
        objectUnderAssert.intType = 1;
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isNotEqualTo(otherObject));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Not_Throw_Exception_When_Equals_Method_Is_Reflexive() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isReflexive());

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Not_Throw_Exception_When_Equals_Method_Is_Symmetric() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isSymmetric(objectUnderAssert));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Not_Throw_Exception_When_Equals_Method_Is_Consistent() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isConsistent());

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Not_Throw_Exception_When_Equals_Method_Is_Transitive() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isTransitive(objectUnderAssert, objectUnderAssert));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Not_Throw_Exception_When_Equals_Method_Return_False_Compared_To_Null() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isNotEqualToNull());

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Not_Throw_Exception_When_Equals_Method_Return_False_Compared_To_Different_Type() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isNotEqualToObjectWithDifferentType(this));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Not_Throw_Exception_When_Equals_Method_Return_False_Compared_To_Not_Equal_Object() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final GoodPojo_Equals_HashCode_ToString otherObject = new GoodPojo_Equals_HashCode_ToString();
        otherObject.booleanType = true;
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isNotEqualTo(otherObject));

        // then
        assertThat(result).isNull();
    }
}
