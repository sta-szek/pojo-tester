package pl.pojo.tester.internal.field.primitive;


class ByteValueChanger extends AbstractPrimitiveValueChanger<Byte> {

    @Override
    protected Byte increase(final Byte value) {
        return (byte) (value + 1);
    }

}
