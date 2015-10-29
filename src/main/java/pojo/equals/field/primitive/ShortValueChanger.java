package pojo.equals.field.primitive;


class ShortValueChanger extends PrimitiveValueChanger<Short> {
    @Override
    public boolean areDifferentValues(Short sourceValue, Short targetValue) {
        return sourceValue.shortValue() != targetValue.shortValue();
    }

    @Override
    protected Short increaseValue(Short value) {
        return Short.valueOf((short) (value.shortValue() + 1));
    }
}
