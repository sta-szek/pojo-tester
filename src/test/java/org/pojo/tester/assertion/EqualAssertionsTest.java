package org.pojo.tester.assertion;

import org.junit.Test;
import test.utils.BadPojoEqualsDifferentType;
import test.utils.BadPojoEqualsNull;
import test.utils.BadPojoEquals_NotSymmetric;
import test.utils.GoodPojo_Equals_HashCode_ToString;

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
        verify(resultBuilder).fail(eq(BadPojoEqualsNull.class),anyString(),anyString());
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
        verify(resultBuilder).fail(eq(BadPojoEquals_NotSymmetric.class),anyString(),anyString());
    }

    @Test
    public void shouldFail_WhenEqualsMethodIsNotTransitive() {
        // given
        final BadPojoEquals_NotSymmetric objectUnderAssert = new BadPojoEquals_NotSymmetric();
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isTransitive(objectUnderAssert, objectUnderAssert);

        // then
        verify(resultBuilder).fail(eq(BadPojoEquals_NotSymmetric.class),anyString(),anyString());
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
        verify(resultBuilder).fail(eq(BadPojoEqualsNull.class),anyString(),anyString());
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
        verify(resultBuilder).fail(eq(BadPojoEqualsDifferentType.class),anyString(),anyString());
    }

    @Test
    public void shouldFail_WhenEqualsMethodReturnTrue_ComparedToNotEqualObject() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final GoodPojo_Equals_HashCode_ToString otherObject = new GoodPojo_Equals_HashCode_ToString();
        objectUnderAssert.intType = 1;
        final ResultBuilder resultBuilder = spy(ResultBuilder.class);
        final EqualAssertions equalAssertions = new EqualAssertions(resultBuilder, objectUnderAssert);

        // when
        equalAssertions.isNotEqualTo(otherObject);

        // then
        verify(resultBuilder).fail(eq(GoodPojo_Equals_HashCode_ToString.class),anyString(),anyString());
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
    public void shouldPass_WhenEqualsMethodReturnFalse_ComparedToNotEqualObject() {
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