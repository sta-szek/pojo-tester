package test;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import test.fields.TestEnum1;

import java.util.Random;

public class GoodPojo_Equals_HashCode_ToString {
    public final long random;
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
}
