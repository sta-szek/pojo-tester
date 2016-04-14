package test.equals;


public class BadPojoEqualsItself {
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
