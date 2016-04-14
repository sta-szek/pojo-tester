package test;


public class TestHelper {
    private final int finalIntType = 0;
    private byte byteType;
    private short shortType;
    private int intType;
    private long longType;
    private double doubleType;
    private boolean booleanType;
    private char characterType;
    private float floatType;

    public TestHelper(final byte byteType) {
        this.byteType = byteType;
    }

    public TestHelper(final float floatType) {
        this.floatType = floatType;
    }

    public TestHelper(final char characterType) {
        this.characterType = characterType;
    }

    public TestHelper(final boolean booleanType) {
        this.booleanType = booleanType;
    }

    public TestHelper(final double doubleType) {
        this.doubleType = doubleType;
    }

    public TestHelper(final long longType) {
        this.longType = longType;
    }

    public TestHelper(final int intType) {
        this.intType = intType;
    }

    public TestHelper(final short shortType) {
        this.shortType = shortType;
    }

    public TestHelper() {
    }
}
