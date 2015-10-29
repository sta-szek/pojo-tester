package pojo.equals.test.pojos;


public class BadPojoEqualsItself {
    private byte byteField;
    private short shortType;
    private int intType;
    private long longType;
    private double doubleType;
    private boolean booleanType;
    private char charType;

    @Override
    public String toString() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        if (o == this) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 1;
    }

}
