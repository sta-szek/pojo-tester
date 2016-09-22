package pl.pojo.tester.internal.field.primitive;


class ByteValueChanger extends AbstractPrimitiveValueChanger<Byte> {

    @Override
    public boolean areDifferent(final Byte sourceValue, final Byte targetValue) {
        return sourceValue.byteValue() != targetValue.byteValue();
    }

    @Override
    protected Byte increase(final Byte value) {
        return (byte) (value + 1);
    }

}
