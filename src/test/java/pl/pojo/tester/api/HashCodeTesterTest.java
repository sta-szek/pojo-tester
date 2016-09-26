package pl.pojo.tester.api;

import classesForTest.fields.TestEnum1;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.internal.assertion.hashcode.HashCodeAssertionError;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;
import static pl.pojo.tester.api.FieldPredicate.exclude;
import static pl.pojo.tester.api.FieldPredicate.include;

@RunWith(JUnitPlatform.class)
public class HashCodeTesterTest {

    @Test
    public void Should_Pass_All_HashCode_Tests() {
        // given
        final Class[] classesToTest = {GoodPojo_Equals_HashCode_ToString.class};
        final HashCodeTester hashCodeTester = new HashCodeTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_HashCode_Tests_Excluding_Fields() {
        // given
        final HashCodeTester hashCodeTester = new HashCodeTester();
        final Class<?> clazz = BadPojoHashCode.class;
        final List<String> excludedFields = newArrayList("increment3", "increment4");

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.test(clazz, exclude(excludedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_HashCode_Tests_Including_Fields() {
        // given
        final HashCodeTester hashCodeTester = new HashCodeTester();
        final Class<?> clazz = BadPojoHashCode.class;
        final List<String> includedFields = newArrayList("increment1", "increment2");

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.test(clazz, include(includedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_Itself_Test() {
        // given
        final Class[] classesToTest = {BadPojoHashCodeItself.class};
        final HashCodeTester hashCodeTester = new HashCodeTester();

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(HashCodeAssertionError.class);
    }

    @Test
    public void Should_Fail_Multiple_Classes() {
        // given
        final Class[] classesToTest = {BadPojoHashCodeItself.class,
                                       BadPojoHashCodeDifferentObjectSameType.class,
                                       BadPojoHashCodeItself.class};
        final HashCodeTester hashCodeTester = new HashCodeTester();

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(HashCodeAssertionError.class);
    }

    @Test
    public void Should_Fail_Different_Object_With_Same_Type() {
        // given
        final Class[] classesToTest = {BadPojoHashCodeDifferentObjectSameType.class};
        final HashCodeTester hashCodeTester = new HashCodeTester();

        // when
        final Throwable result = catchThrowable(() -> hashCodeTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(HashCodeAssertionError.class);
    }

    private static class BadPojoHashCodeDifferentObjectSameType {

        private static int increment;

        @Override
        public int hashCode() {
            return BadPojoHashCodeDifferentObjectSameType.increment++;
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

    private class BadPojoHashCode {

        private int increment1;
        private int increment2;
        private int increment3;
        private int increment4;

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(increment1)
                                        .append(increment2)
                                        .toHashCode();
        }
    }

    class BadPojoHashCodeItself {

        private int increment;

        @Override
        public int hashCode() {
            return increment++;
        }
    }
}
