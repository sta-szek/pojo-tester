package pojo.equals.field.primitive;


class ByteValueChanger extends PrimitiveValueChanger<Byte> {

    @Override
    public boolean areDifferentValues(Byte sourceValue, Byte targetValue) {
        return sourceValue.byteValue() != targetValue.byteValue();
    }

    @Override
    protected Byte increaseValue(Byte value) {
        return Byte.valueOf((byte) (value.byteValue() + 1));
    }

}
