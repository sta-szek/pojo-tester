package test.utils;


import org.apache.commons.lang3.builder.ToStringBuilder;

public class BadPojoEquals_NotSymmetric {
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
