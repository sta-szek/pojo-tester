package org.pojo.tester.assertion;

import org.junit.Test;
import test.GoodPojo_Equals_HashCode_ToString;
import test.equals.*;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


public class EqualAssertionsTest {

    @Test
    public void Should_Fail_When_Equals_Method_Is_Not_Reflexive() {
        // given
        final BadPojoEqualsNull objectUnderAssert = new BadPojoEqualsNull();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isReflexive();

        // then
        verify(resultBuilder).fail(eq(BadPojoEqualsNull.class), anyString(), anyString());
    }

    @Test
    public void Should_Fail_When_Equals_Method_Is_Not_Symmetric() {
        // given
        final BadPojoEquals_NotSymmetric objectUnderAssert = new BadPojoEquals_NotSymmetric();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isSymmetric(objectUnderAssert);

        // then
        verify(resultBuilder).fail(eq(BadPojoEquals_NotSymmetric.class), anyString(), anyString());
    }

    @Test
    public void Should_Fail_When_Equals_Method_Is_Not_Consistent() {
        // given
        final BadPojoEquals_NotConsistent objectUnderAssert = new BadPojoEquals_NotConsistent();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isConsistent();

        // then
        verify(resultBuilder).fail(eq(BadPojoEquals_NotConsistent.class), anyString(), anyString());
    }

    @Test
    public void Should_Fail_When_Equals_Method_Is_Not_Transitive_Between_A_And_B() {
        // given
        final BadPojoEquals_NotTransitive_A_B objectUnderAssert = new BadPojoEquals_NotTransitive_A_B();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isTransitive(objectUnderAssert, objectUnderAssert);

        // then
        verify(resultBuilder).fail(eq(BadPojoEquals_NotTransitive_A_B.class), anyString(), anyString());
    }

    @Test
    public void Should_Fail_When_Equals_Method_Is_Not_Transitive_Between_B_And_C() {
        // given
        final BadPojoEquals_NotTransitive_B_C objectUnderAssert = new BadPojoEquals_NotTransitive_B_C();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isTransitive(objectUnderAssert, objectUnderAssert);

        // then
        verify(resultBuilder).fail(eq(BadPojoEquals_NotTransitive_B_C.class), anyString(), anyString());
    }

    @Test
    public void Should_Fail_When_Equals_Method_Return_True_Compared_To_Null() {
        // given
        final BadPojoEqualsNull objectUnderAssert = new BadPojoEqualsNull();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isNotEqualToNull();

        // then
        verify(resultBuilder).fail(eq(BadPojoEqualsNull.class), anyString(), anyString());
    }

    @Test
    public void Should_Fail_When_Equals_Method_Return_True_Compared_To_Different_Type() {
        // given
        final BadPojoEqualsDifferentType objectUnderAssert = new BadPojoEqualsDifferentType();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isNotEqualToObjectWithDifferentType(this);

        // then
        verify(resultBuilder).fail(eq(BadPojoEqualsDifferentType.class), anyString(), anyString());
    }

    @Test
    public void Should_Fail_When_Equals_Method_Return_True_Compared_To_Not_Equal_Object() {
        // given
        final BadPojoEqualsWithEqualObject objectUnderAssert = new BadPojoEqualsWithEqualObject();
        final BadPojoEqualsWithEqualObject otherObject = new BadPojoEqualsWithEqualObject();
        objectUnderAssert.intType = 1;
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isNotEqualTo(otherObject);

        // then
        verify(resultBuilder).fail(eq(BadPojoEqualsWithEqualObject.class), anyString(), anyString());
    }

    @Test
    public void Should_Pass_When_Equals_Method_Is_Reflexive() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isReflexive();

        // then
        verify(resultBuilder).pass(eq(GoodPojo_Equals_HashCode_ToString.class), anyString());
    }

    @Test
    public void Should_Pass_When_Equals_Method_Is_Symmetric() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isSymmetric(objectUnderAssert);

        // then
        verify(resultBuilder).pass(eq(GoodPojo_Equals_HashCode_ToString.class), anyString());
    }

    @Test
    public void Should_Pass_When_Equals_Method_Is_Consistent() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isConsistent();

        // then
        verify(resultBuilder).pass(eq(GoodPojo_Equals_HashCode_ToString.class), anyString());
    }

    @Test
    public void Should_Pass_When_Equals_Method_Is_Transitive() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isTransitive(objectUnderAssert, objectUnderAssert);

        // then
        verify(resultBuilder).pass(eq(GoodPojo_Equals_HashCode_ToString.class), anyString());
    }

    @Test
    public void Should_Pass_When_Equals_Method_Return_False_Compared_To_Null() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isNotEqualToNull();

        // then
        verify(resultBuilder).pass(eq(GoodPojo_Equals_HashCode_ToString.class), anyString());
    }

    @Test
    public void Should_Pass_When_Equals_Method_Return_False_Compared_To_Different_Type() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isNotEqualToObjectWithDifferentType(this);

        // then
        verify(resultBuilder).pass(eq(GoodPojo_Equals_HashCode_ToString.class), anyString());
    }

    @Test
    public void Should_Fail_When_Equals_Method_Return_False_Compared_To_Not_Equal_Object() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final GoodPojo_Equals_HashCode_ToString otherObject = new GoodPojo_Equals_HashCode_ToString();
        otherObject.booleanType = true;
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isNotEqualTo(otherObject);

        // then
        verify(resultBuilder).pass(eq(GoodPojo_Equals_HashCode_ToString.class), anyString());
    }
}
