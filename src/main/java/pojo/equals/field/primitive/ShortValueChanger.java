package pojo.equals.field.primitive;


class ShortValueChanger extends PrimitiveValueChanger<Short> {

    @Override
    public boolean areDifferentValues(final Short sourceValue, final Short targetValue) {
        return sourceValue.shortValue() != targetValue.shortValue();
    }

    @Override
    protected Short increaseValue(final Short value) {
        return (short) (value + 1);
    }
}
