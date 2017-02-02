package pl.pojo.tester.internal.assertion.equals;


import classesForTest.fields.TestEnum1;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


public class EqualAssertionsTest {

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Is_Not_Reflexive() {
        // given
        final BadPojoEqualsNull objectUnderAssert = new BadPojoEqualsNull();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(equalAssertions::isReflexive);

        // then
        assertThat(result).isInstanceOf(ReflexiveEqualsAssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Is_Not_Consistent_1() {
        // given
        final BadPojoEquals_NotConsistent objectUnderAssert = new BadPojoEquals_NotConsistent(true, false);
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(equalAssertions::isConsistent);

        // then
        assertThat(result).isInstanceOf(ConsistentEqualsAssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Is_Not_Consistent_2() {
        // given
        final BadPojoEquals_NotConsistent objectUnderAssert = new BadPojoEquals_NotConsistent(false, true);
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(equalAssertions::isConsistent);

        // then
        assertThat(result).isInstanceOf(ConsistentEqualsAssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Is_Not_Symmetric() {
        // given
        final BadPojoEquals_NotSymmetric objectUnderAssert = new BadPojoEquals_NotSymmetric();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isSymmetric(objectUnderAssert));

        // then
        assertThat(result).isInstanceOf(SymmetricEqualsAssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Is_Not_Transitive_Between_A_And_B() {
        // given
        final BadPojoEquals_NotTransitive_A_B objectUnderAssert = new BadPojoEquals_NotTransitive_A_B();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isTransitive(objectUnderAssert, objectUnderAssert));

        // then
        assertThat(result).isInstanceOf(TransitiveEqualsAssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Is_Not_Transitive_Between_B_And_C() {
        // given
        final BadPojoEquals_NotTransitive_B_C objectUnderAssert = new BadPojoEquals_NotTransitive_B_C();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isTransitive(objectUnderAssert, objectUnderAssert));

        // then
        assertThat(result).isInstanceOf(TransitiveEqualsAssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Return_True_Compared_To_Null() {
        // given
        final BadPojoEqualsNull objectUnderAssert = new BadPojoEqualsNull();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(equalAssertions::isNotEqualToNull);

        // then
        assertThat(result).isInstanceOf(NullEqualsAssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Return_True_Compared_To_Different_Type() {
        // given
        final BadPojoEqualsDifferentType objectUnderAssert = new BadPojoEqualsDifferentType();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isNotEqualToObjectWithDifferentType(this));

        // then
        assertThat(result).isInstanceOf(OtherTypeEqualsAssertionError.class);
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
        assertThat(result).isInstanceOf(NotEqualEqualsAssertionError.class);
    }

    @Test
    public void Should_Throw_Exception_When_Equals_Method_Return_False_Compared_To_Equal_Object() {
        // given
        final BadPojoEquals_NotConsistent objectUnderAssert = new BadPojoEquals_NotConsistent(false, false);
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isEqualTo(objectUnderAssert));

        // then
        assertThat(result).isInstanceOf(EqualEqualsAssertionError.class);
    }

    @Test
    public void Should_Not_Throw_Exception_When_Equals_Method_Is_Reflexive() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(equalAssertions::isReflexive);

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Not_Throw_Exception_When_Equals_Method_Is_Consistent() {
        // given
        final GoodPojo_Equals_HashCode_ToString objectUnderAssert = new GoodPojo_Equals_HashCode_ToString();
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(equalAssertions::isConsistent);

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
        final Throwable result = catchThrowable(equalAssertions::isNotEqualToNull);

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

    @Test
    public void Should_Not_Throw_Exception_When_Equals_Method_Return_True_Compared_To_Equal_Object() {
        // given
        final BadPojoEquals_NotConsistent objectUnderAssert = new BadPojoEquals_NotConsistent(true, false);
        final EqualAssertions equalAssertions = new EqualAssertions(objectUnderAssert);

        // when
        final Throwable result = catchThrowable(() -> equalAssertions.isEqualTo(objectUnderAssert));

        // then
        assertThat(result).isNull();
    }

    private static class BadPojoEquals_NotTransitive_A_B {

        private static int counter = -1;

        @Override
        public boolean equals(final Object obj) {
            BadPojoEquals_NotTransitive_A_B.counter++;
            return BadPojoEquals_NotTransitive_A_B.counter % 3 == 0;
        }
    }

    private static class BadPojoEquals_NotTransitive_B_C {

        private static int counter = 0;

        @Override
        public boolean equals(final Object obj) {
            BadPojoEquals_NotTransitive_B_C.counter++;
            return BadPojoEquals_NotTransitive_B_C.counter % 3 == 0;
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

    private class BadPojoEquals_NotConsistent {

        private final boolean[] equalReturnValues = new boolean[2];
        private int counter = -1;

        public BadPojoEquals_NotConsistent(final boolean firstReturn, final boolean secondReturn) {
            this.equalReturnValues[0] = firstReturn;
            this.equalReturnValues[1] = secondReturn;
        }

        @Override
        public boolean equals(final Object obj) {
            counter++;
            return equalReturnValues[counter];
        }
    }

    private class BadPojoEquals_NotSymmetric {
        private byte byteField;
        private short shortType;
        private int intType;
        private long longType;
        private double doubleType;
        private boolean booleanType;
        private char charType;
        private float floatType;

        private boolean flipFlop;

        @Override
        public boolean equals(final Object o) {
            flipFlop = !flipFlop;
            return flipFlop;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("byteField", byteField)
                    .append("shortType", shortType)
                    .append("intType", intType)
                    .append("longType", longType)
                    .append("doubleType", doubleType)
                    .append("booleanType", booleanType)
                    .append("charType", charType)
                    .append("floatType", floatType)
                    .toString();
        }
    }

    private class BadPojoEqualsDifferentType {
        private byte byteField;
        private short shortType;
        private int intType;
        private long longType;
        private double doubleType;
        private boolean booleanType;
        private char charType;
        private float floatType;

        @Override
        public String toString() {
            return "";
        }

        @Override
        public boolean equals(final Object o) {
            if (o == null) {
                return false;
            }
            return o.getClass() != getClass();
        }

        @Override
        public int hashCode() {
            return 1;
        }

    }

    private class BadPojoEqualsNull {
        private byte byteField;
        private short shortType;
        private int intType;
        private long longType;
        private double doubleType;
        private boolean booleanType;
        private char charType;
        private float floatType;

        @Override
        public boolean equals(final Object o) {
            return o == null;
        }

        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("byteField", byteField)
                    .append("shortType", shortType)
                    .append("intType", intType)
                    .append("longType", longType)
                    .append("doubleType", doubleType)
                    .append("booleanType", booleanType)
                    .append("charType", charType)
                    .append("floatType", floatType)
                    .toString();
        }
    }

    private class BadPojoEqualsWithEqualObject {
        public byte byteField;
        public short shortType;
        public int intType;
        public long longType;
        public double doubleType;
        public boolean booleanType;
        public char charType;
        public float floatType;

        @Override
        public String toString() {
            return "";
        }

        @Override
        public boolean equals(final Object o) {
            return !trueEqual(o);
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(byteField)
                    .append(shortType)
                    .append(intType)
                    .append(longType)
                    .append(doubleType)
                    .append(booleanType)
                    .append(charType)
                    .append(floatType)
                    .toHashCode();
        }

        private boolean trueEqual(final Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final BadPojoEqualsWithEqualObject that = (BadPojoEqualsWithEqualObject) o;

            return new EqualsBuilder()
                    .append(byteField, that.byteField)
                    .append(shortType, that.shortType)
                    .append(intType, that.intType)
                    .append(longType, that.longType)
                    .append(doubleType, that.doubleType)
                    .append(booleanType, that.booleanType)
                    .append(charType, that.charType)
                    .append(floatType, that.floatType)
                    .isEquals();
        }
    }

}
