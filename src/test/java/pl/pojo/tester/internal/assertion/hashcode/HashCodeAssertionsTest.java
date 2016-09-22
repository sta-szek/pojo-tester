package pl.pojo.tester.internal.assertion.hashcode;

import classesForTest.GoodPojo_Equals_HashCode_ToString;
import classesForTest.hashcode.BadPojoHashCode_DifferentObjects;
import classesForTest.hashcode.BadPojoHashCode_NotConsistent;
import classesForTest.hashcode.BadPojoHashCode_SameObjects;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(JUnitPlatform.class)
public class HashCodeAssertionsTest {

    @Test
    public void Should_Throw_Exception_When_HashCode_Method_Is_Not_Consistent() {
        // given
        final BadPojoHashCode_NotConsistent objectUnderAssert = new BadPojoHashCode_NotConsistent();
        final HashCodeAssertions hashCodeAssertions = new HashCodeAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(hashCodeAssertions::isConsistent);

        // then
        assertThat(result).isInstanceOf(ConsistentHashCodeAssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_HashCode_Method_Returns_Different_HashCode_For_Same_Objects() {
        // given
        final BadPojoHashCode_SameObjects objectUnderAssert = new BadPojoHashCode_SameObjects();
        final HashCodeAssertions hashCodeAssertions = new HashCodeAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> hashCodeAssertions.returnsSameValueFor(objectUnderAssert));

        // then
        assertThat(result).isInstanceOf(EqualHashCodeAssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_HashCode_Method_Returns_Same_HashCode_For_Different_Objects() {
        // given
        final BadPojoHashCode_DifferentObjects objectUnderAssert1 = new BadPojoHashCode_DifferentObjects(1);
        final BadPojoHashCode_DifferentObjects objectUnderAssert2 = new BadPojoHashCode_DifferentObjects(2);
        final HashCodeAssertions hashCodeAssertions = new HashCodeAssertions(objectUnderAssert1);

        // when
        final Throwable result = catchThrowable(() -> hashCodeAssertions.returnsDifferentValueFor(objectUnderAssert2));

        // then
        assertThat(result).isInstanceOf(NotEqualHashCodeAssertionError.class);
    }

    @Test
    public void Should_Not_Throw_Exception_When_HashCode_Method_Returns_Different_HashCode_For_Different_Objects() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert1 = new GoodPojo_Equals_HashCode_ToString();
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert2 = new GoodPojo_Equals_HashCode_ToString();
        objectUnderAssert2.booleanType = true;
        final HashCodeAssertions hashCodeAssertions = new HashCodeAssertions(objectUnderAssert1);

        // when
        final Throwable result = catchThrowable(() -> hashCodeAssertions.returnsDifferentValueFor(objectUnderAssert2));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Not_Throw_Exception_When_HashCode_Method_Returns_Same_HashCode_For_Same_Objects() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final HashCodeAssertions hashCodeAssertions = new HashCodeAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> hashCodeAssertions.returnsSameValueFor(objectUnderAssert));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Not_Throw_Exception_When_HashCode_Method_Is_Consistent() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final HashCodeAssertions hashCodeAssertions = new HashCodeAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(hashCodeAssertions::isConsistent);

        // then
        assertThat(result).isNull();
    }

}
