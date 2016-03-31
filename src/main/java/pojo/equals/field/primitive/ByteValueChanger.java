package pojo.equals.field.primitive;


class ByteValueChanger extends PrimitiveValueChanger<Byte> {

    @Override
    public boolean areDifferentValues(final Byte sourceValue, final Byte targetValue) {
        return sourceValue.byteValue() != targetValue.byteValue();
    }

    @Override
    protected Byte increaseValue(final Byte value) {
        return (byte) (value + 1);
    }

}
