package pl.pojo.tester.api;

import classesForTest.fields.TestEnum1;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import pl.pojo.tester.internal.assertion.AssertionError;
import pl.pojo.tester.internal.field.DefaultFieldValueChanger;
import pl.pojo.tester.internal.tester.ToStringTester;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.util.Lists.newArrayList;

@RunWith(JUnitPlatform.class)
public class ToStringTesterTest {

    @Test
    public void Should_Pass_All_ToString_Tests() {
        // given
        final Class[] classesToTest = {GoodPojo_Equals_HashCode_ToString.class};
        final ToStringTester toStringTester = new ToStringTester(DefaultFieldValueChanger.INSTANCE);

        // when
        final Throwable result = catchThrowable(() -> toStringTester.testAll(classesToTest));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_ToString_Tests_Excluding_Fields() {
        // given
        final ToStringTester toStringTester = new ToStringTester();
        final Class<?> clazz = ToStringWithoutField.class;
        final List<String> excludedFields = newArrayList("testEnum");

        // when
        final Throwable result = catchThrowable(() -> toStringTester.test(clazz,
                                                                          FieldPredicate.exclude(excludedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Pass_All_ToString_Tests_Including_Fields() {
        // given
        final ToStringTester toStringTester = new ToStringTester();
        final Class<?> clazz = ToStringWithoutField.class;
        final List<String> includedFields = newArrayList("a", "b", "obj");

        // when
        final Throwable result = catchThrowable(() -> toStringTester.test(clazz,
                                                                          FieldPredicate.include(includedFields)));

        // then
        assertThat(result).isNull();
    }

    @Test
    public void Should_Fail_All_ToString_Tests() {
        // given
        final Class[] classesToTest = {ToStringWithoutField.class};
        final ToStringTester toStringTester = new ToStringTester();

        // when
        final Throwable result = catchThrowable(() -> toStringTester.testAll(classesToTest));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Fail_All_ToString_Tests_Excluding_Fields() {
        // given
        final ToStringTester toStringTester = new ToStringTester();
        final Class<?> clazz = ToStringWithoutField.class;
        final List<String> excludedFields = newArrayList("a");

        // when
        final Throwable result = catchThrowable(() -> toStringTester.test(clazz,
                                                                          FieldPredicate.exclude(excludedFields)));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
    }

    @Test
    public void Should_Fail_All_ToString_Tests_Including_Fields() {
        // given
        final ToStringTester toStringTester = new ToStringTester();
        final Class<?> clazz = ToStringWithoutField.class;
        final List<String> includedFields = newArrayList("a", "b");

        // when
        final Throwable result = catchThrowable(() -> toStringTester.test(clazz,
                                                                          FieldPredicate.include(includedFields)));

        // then
        assertThat(result).isInstanceOf(AssertionError.class);
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

    private class ToStringWithoutField {

        private final int a = 1;
        private final float b = 1.43F;
        private final Object obj = null;
        private TestEnum1 testEnum;

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("a", a)
                                            .append("b", b)
                                            .append("obj", obj)
                                            .toString();
        }
    }
}
