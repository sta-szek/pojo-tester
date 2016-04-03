package test.utils;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public GoodPojo_Equals_HashCode_ToString() {
        final Random random = new Random();
        this.random = random.nextLong();
    }

    @Override
    public String toString() {
        return "PojoWithWellImplementedMethods{" +
               "byteField=" + byteField +
               ", shortType=" + shortType +
               ", intType=" + intType +
               ", longType=" + longType +
               ", doubleType=" + doubleType +
               ", booleanType=" + booleanType +
               ", charType=" + charType +
               '}';
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

        return new EqualsBuilder()
                .append(byteField, that.byteField)
                .append(shortType, that.shortType)
                .append(intType, that.intType)
                .append(longType, that.longType)
                .append(doubleType, that.doubleType)
                .append(booleanType, that.booleanType)
                .append(charType, that.charType)
                .isEquals();
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
                .toHashCode();
    }

}
