package pl.pojo.tester.internal.tester;

import classesForTest.fields.TestEnum1;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.api.FieldPredicate;
import pl.pojo.tester.internal.assertion.equals.AbstractEqualsAssertionError;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;

@RunWith(JUnitPlatform.class)
public class EqualsTesterTest {

    @Test
    public void Should_Pass_All_Equals_Tests() {
        // given
        final Class[] classesToTest = {GoodPojo_Equals_HashCode_ToString.class};
        final EqualsTester equalsTester = new EqualsTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_Equals_Tests_Excluding_Fields() {
        // given
        final EqualsTester equalsTester = new EqualsTester();
        final Class<?> clazz = BadPojoEqualsDifferentObjectSameType.class;
        final ArrayList<String> excludedFields = newArrayList("notIncludedToEqual_byteField",
                                                              "notIncludedToEqual_shortType");

        // when
        final Throwable result = catchThrowable(() -> equalsTester.test(clazz, FieldPredicate.exclude(excludedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_Equals_Tests_Including_Fields() {
        // given
        final EqualsTester equalsTester = new EqualsTester();
        final Class<?> clazz = BadPojoEqualsDifferentObjectSameType.class;
        final ArrayList<String> includedFields = newArrayList("byteField", "shortType");

        // when
        final Throwable result = catchThrowable(() -> equalsTester.test(clazz, FieldPredicate.include(includedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_Null_Test() {
        // given
        final Class[] classesToTest = {BadPojoEqualsNull.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(AbstractEqualsAssertionError.class);
    }

    @Test
    public void Should_Fail_Itself_Test() {
        // given
        final Class[] classesToTest = {BadPojoEqualsItself.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(AbstractEqualsAssertionError.class);
    }

    @Test
    public void Should_Fail_Different_Type_Test() {
        // given
        final Class[] classesToTest = {BadPojoEqualsDifferentType.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(AbstractEqualsAssertionError.class);
    }

    @Test
    public void Should_Fail_Multiple_Classes() {
        // given
        final Class[] classesToTest = {BadPojoEqualsNull.class,
                                       BadPojoEqualsDifferentType.class,
                                       BadPojoEqualsItself.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(AbstractEqualsAssertionError.class);
    }

    @Test
    public void Should_Fail_Different_Object_With_Same_Type() {
        // given
        final Class[] classesToTest = {BadPojoEqualsDifferentObjectSameType.class};
        final EqualsTester equalsTester = new EqualsTester();

        // when
        final Throwable result = catchThrowable(() -> equalsTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(AbstractEqualsAssertionError.class);
    }

    @Test
    public void Should_Fail_When_Equals_Implementation_Depends_On_Excluded_Field() {
        // given
        final EqualsTester equalsTester = new EqualsTester();
        final Class<?> classToTest = GoodPojo_Equals_HashCode_ToString.class;
        final Predicate<String> includedFields = FieldPredicate.include("byteField");

        // when
        final Throwable result = catchThrowable(() -> equalsTester.test(classToTest, includedFields));

        // then
        assertThat(result).isInstanceOf(AbstractEqualsAssertionError.class);
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

    class BadPojoEqualsDifferentObjectSameType {
        private byte byteField;
        private short shortType;
        private byte notIncludedToEqual_byteField;
        private short notIncludedToEqual_shortType;

        @Override
        public String toString() {
            return byteField + " " +
                   shortType + " " +
                   notIncludedToEqual_byteField + " " +
                   notIncludedToEqual_shortType;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final BadPojoEqualsDifferentObjectSameType that = (BadPojoEqualsDifferentObjectSameType) o;

            return new EqualsBuilder().append(byteField, that.byteField)
                                      .append(shortType, that.shortType)
                                      .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(byteField)
                                        .append(shortType)
                                        .toHashCode();
        }
    }

    class BadPojoEqualsDifferentType {
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

    class BadPojoEqualsItself {
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
            if (o == null || o.getClass() != getClass()) {
                return false;
            }
            return o != this;
        }

        @Override
        public int hashCode() {
            return 1;
        }

    }

    class BadPojoEqualsNull {
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


}
