package classesForTest.fields;


public class AllFiledTypes {
    private final int finalIntType = 0;
    private byte byteType;
    private short shortType;
    private int intType;
    private long longType;
    private double doubleType;
    private boolean booleanType;
    private char characterType;
    private float floatType;

    public AllFiledTypes(final byte byteType) {
        this.byteType = byteType;
    }

    public AllFiledTypes(final float floatType) {
        this.floatType = floatType;
    }

    public AllFiledTypes(final char characterType) {
        this.characterType = characterType;
    }

    public AllFiledTypes(final boolean booleanType) {
        this.booleanType = booleanType;
    }

    public AllFiledTypes(final double doubleType) {
        this.doubleType = doubleType;
    }

    public AllFiledTypes(final long longType) {
        this.longType = longType;
    }

    public AllFiledTypes(final int intType) {
        this.intType = intType;
    }

    public AllFiledTypes(final short shortType) {
        this.shortType = shortType;
    }

    public AllFiledTypes() {
    }
}
