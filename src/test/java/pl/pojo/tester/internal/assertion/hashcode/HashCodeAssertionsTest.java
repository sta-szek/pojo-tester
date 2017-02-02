package pl.pojo.tester.internal.assertion.hashcode;

import classesForTest.fields.TestEnum1;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


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

    private static class BadPojoHashCode_NotConsistent {

        private static int counter = 0;

        @Override
        public int hashCode() {
            BadPojoHashCode_NotConsistent.counter++;
            return BadPojoHashCode_NotConsistent.counter % 2;
        }
    }

    private static class BadPojoHashCode_SameObjects {

        private static int counter = 0;

        @Override
        public int hashCode() {
            BadPojoHashCode_SameObjects.counter++;
            return BadPojoHashCode_SameObjects.counter;
        }
    }

    private class GoodPojo_Equals_HashCode_ToString {
        public long random;
        public byte byteField;
        public short shortType;
        public int intType;
        public long longType;
        public double doubleType;
        public boolean booleanType;
        public float floatType;
        public char charType;
        public TestEnum1 testEnum1;

        public GoodPojo_Equals_HashCode_ToString() {
            final Random random = new Random();
            this.random = random.nextLong();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("random", random)
                                            .append("byteField", byteField)
                                            .append("shortType", shortType)
                                            .append("intType", intType)
                                            .append("longType", longType)
                                            .append("doubleType", doubleType)
                                            .append("booleanType", booleanType)
                                            .append("floatType", floatType)
                                            .append("charType", charType)
                                            .append("testEnum1", testEnum1)
                                            .toString();
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final GoodPojo_Equals_HashCode_ToString that = (GoodPojo_Equals_HashCode_ToString) o;

            return new EqualsBuilder().append(random, that.random)
                                      .append(byteField, that.byteField)
                                      .append(shortType, that.shortType)
                                      .append(intType, that.intType)
                                      .append(longType, that.longType)
                                      .append(doubleType, that.doubleType)
                                      .append(booleanType, that.booleanType)
                                      .append(floatType, that.floatType)
                                      .append(charType, that.charType)
                                      .append(testEnum1, that.testEnum1)
                                      .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(random)
                                        .append(byteField)
                                        .append(shortType)
                                        .append(intType)
                                        .append(longType)
                                        .append(doubleType)
                                        .append(booleanType)
                                        .append(floatType)
                                        .append(charType)
                                        .append(testEnum1)
                                        .toHashCode();
        }

        public long getRandom() {
            return random;
        }

        public void setRandom(final long random) {
            this.random = random;
        }

        public byte getByteField() {
            return byteField;
        }

        public void setByteField(final byte byteField) {
            this.byteField = byteField;
        }

        public short getShortType() {
            return shortType;
        }

        public void setShortType(final short shortType) {
            this.shortType = shortType;
        }

        public int getIntType() {
            return intType;
        }

        public void setIntType(final int intType) {
            this.intType = intType;
        }

        public long getLongType() {
            return longType;
        }

        public void setLongType(final long longType) {
            this.longType = longType;
        }

        public double getDoubleType() {
            return doubleType;
        }

        public void setDoubleType(final double doubleType) {
            this.doubleType = doubleType;
        }

        public boolean isBooleanType() {
            return booleanType;
        }

        public void setBooleanType(final boolean booleanType) {
            this.booleanType = booleanType;
        }

        public float getFloatType() {
            return floatType;
        }

        public void setFloatType(final float floatType) {
            this.floatType = floatType;
        }

        public char getCharType() {
            return charType;
        }

        public void setCharType(final char charType) {
            this.charType = charType;
        }

        public TestEnum1 getTestEnum1() {
            return testEnum1;
        }

        public void setTestEnum1(final TestEnum1 testEnum1) {
            this.testEnum1 = testEnum1;
        }
    }

    private class BadPojoHashCode_DifferentObjects {

        int a;

        public BadPojoHashCode_DifferentObjects(final int a) {
            this.a = a;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }
}
