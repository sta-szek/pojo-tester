package pojo.equals.test.pojos;


public class BadPojoEqualsNull {
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
        if (o == null) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

}
