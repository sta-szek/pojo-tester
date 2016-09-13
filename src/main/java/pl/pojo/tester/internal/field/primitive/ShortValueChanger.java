package pl.pojo.tester.internal.field.primitive;


class ShortValueChanger extends AbstractPrimitiveValueChanger<Short> {

    @Override
    public boolean areDifferent(final Short sourceValue, final Short targetValue) {
        return sourceValue.shortValue() != targetValue.shortValue();
    }

    @Override
    public Short increaseValue(final Short value, final Class<?> type) {
        return (short) (value + 1);
    }
}
