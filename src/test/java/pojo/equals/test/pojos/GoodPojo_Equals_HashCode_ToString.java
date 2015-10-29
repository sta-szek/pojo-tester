package pojo.equals.test.pojos;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Random;

public class GoodPojo_Equals_HashCode_ToString {
    private byte byteField;
    private short shortType;
    private int intType;
    private long longType;
    private double doubleType;
    private boolean booleanType;
    private char charType;
    private long random;

    public GoodPojo_Equals_HashCode_ToString() {
        Random random = new Random();
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
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GoodPojo_Equals_HashCode_ToString that = (GoodPojo_Equals_HashCode_ToString) o;

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
