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
    public void shouldFail_WhenEqualsMethodIsNotReflexive() {
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
    public void shouldFail_WhenEqualsMethodIsNotSymmetric() {
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
    public void shouldFail_WhenEqualsMethodIsNotConsistent() {
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
    public void shouldFail_WhenEqualsMethodIsNotTransitive_BetweenAAndB() {
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
    public void shouldFail_WhenEqualsMethodIsNotTransitive_BetweenBAndC() {
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
    public void shouldFail_WhenEqualsMethodReturnTrue_ComparedToNull() {
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
    public void shouldFail_WhenEqualsMethodReturnTrue_ComparedToDifferentType() {
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
    public void shouldFail_WhenEqualsMethodReturnTrue_ComparedToNotEqualObject() {
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
    public void shouldPass_WhenEqualsMethodIsReflexive() {
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
    public void shouldPass_WhenEqualsMethodIsSymmetric() {
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
    public void shouldPass_WhenEqualsMethodIsConsistent() {
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
    public void shouldPass_WhenEqualsMethodIsTransitive() {
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
    public void shouldPass_WhenEqualsMethodReturnFalse_ComparedToNull() {
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
    public void shouldPass_WhenEqualsMethodReturnFalse_ComparedToDifferentType() {
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
    public void shouldFail_WhenEqualsMethodReturnFalse_ComparedToNotEqualObject() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final GoodPojo_Equals_HashCode_ToString otherObject = new GoodPojo_Equals_HashCode_ToString();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isNotEqualTo(otherObject);

        // then
        verify(resultBuilder).pass(eq(GoodPojo_Equals_HashCode_ToString.class), anyString());
    }
}
