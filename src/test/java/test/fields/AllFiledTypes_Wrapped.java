package test.fields;


public class AllFiledTypes_Wrapped {
    private final Integer finalIntType = 0;
    private Byte byteType;
    private Short shortType;
    private Integer intType;
    private Long longType;
    private Double doubleType;
    private Boolean booleanType;
    private Character characterType;
    private Float floatType;

    public AllFiledTypes_Wrapped(final Byte byteType) {
        this.byteType = byteType;
    }

    public AllFiledTypes_Wrapped(final Float floatType) {
        this.floatType = floatType;
    }

    public AllFiledTypes_Wrapped(final Character characterType) {
        this.characterType = characterType;
    }

    public AllFiledTypes_Wrapped(final Boolean booleanType) {
        this.booleanType = booleanType;
    }

    public AllFiledTypes_Wrapped(final Double doubleType) {
        this.doubleType = doubleType;
    }

    public AllFiledTypes_Wrapped(final Long longType) {
        this.longType = longType;
    }

    public AllFiledTypes_Wrapped(final Integer intType) {
        this.intType = intType;
    }

    public AllFiledTypes_Wrapped(final Short shortType) {
        this.shortType = shortType;
    }

    public AllFiledTypes_Wrapped() {
    }
}
