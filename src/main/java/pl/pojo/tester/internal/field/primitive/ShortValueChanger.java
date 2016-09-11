package pl.pojo.tester.internal.field.primitive;


class ShortValueChanger extends AbstractPrimitiveValueChanger<Short> {

    @Override
    public boolean areDifferent(final Short sourceValue, final Short targetValue) {
        return sourceValue.shortValue() != targetValue.shortValue();
    }

    @Override
    protected Short increaseValue(final Short value) {
        return (short) (value + 1);
    }
}
