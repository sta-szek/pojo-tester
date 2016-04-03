package test.utils;

public class BadPojoEqualsDifferentType {
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
        if (o.getClass() != getClass()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

}
