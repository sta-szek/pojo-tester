package pl.pojo.tester.api;

import classesForTest.fields.TestEnum1;
import java.util.Random;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


public class ClassAndFieldPredicatePairTest {

    @Test
    public void Should_Create_Predicate_That_Accepts_All_Fields() {
        // given
        final Class<?> clazz = GoodPojo_Equals_HashCode_ToString.class;
        final String[] expectedFields = new String[]{"random",
                                                     "byteField",
                                                     "shortType",
                                                     "intType",
                                                     "longType",
                                                     "doubleType",
                                                     "booleanType",
                                                     "floatType",
                                                     "charType",
                                                     "testEnum1"};
        // when
        final ClassAndFieldPredicatePair result = new ClassAndFieldPredicatePair(clazz);

        // then
        assertThat(result.getFieldsPredicate()).accepts(expectedFields);
    }

    @Test
    public void Should_Create_Predicate_That_Accepts_All_Fields_By_Class_Name() {
        // given
        final Class<?> expectedClass = GoodPojo_Equals_HashCode_ToString.class;
        final String[] expectedFields = new String[]{"random",
                                                     "byteField",
                                                     "shortType",
                                                     "intType",
                                                     "longType",
                                                     "doubleType",
                                                     "booleanType",
                                                     "floatType",
                                                     "charType",
                                                     "testEnum1"};

        // when
        final ClassAndFieldPredicatePair result = new ClassAndFieldPredicatePair(expectedClass.getName());

        // then
        assertThat(result.getClazz()).isEqualTo(expectedClass);
        assertThat(result.getFieldsPredicate()).accepts(expectedFields);
    }

    @Test
    public void Should_Create_Predicate_That_Accepts_Given_Fields_By_Class_Name() {
        // given
        final Class<?> expectedClass = GoodPojo_Equals_HashCode_ToString.class;
        final String[] expectedFields = new String[]{"random",
                                                     "byteField",
                                                     "shortType",
                                                     "intType",
                                                     "longType",
                                                     "doubleType",
                                                     "booleanType",
                                                     "floatType",
                                                     "charType",
                                                     "testEnum1"};

        // when
        final ClassAndFieldPredicatePair result = new ClassAndFieldPredicatePair(expectedClass.getName(), FieldPredicate.includeAllFields(expectedClass));

        // then
        assertThat(result.getClazz()).isEqualTo(expectedClass);
        assertThat(result.getFieldsPredicate()).accepts(expectedFields);
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
}
