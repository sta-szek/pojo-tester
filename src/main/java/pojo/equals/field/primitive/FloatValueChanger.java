package pojo.equals.field.primitive;


class FloatValueChanger extends PrimitiveValueChanger<Float> {

    @Override
    public boolean areDifferentValues(final Float sourceValue, final Float targetValue) {
        return sourceValue.doubleValue() != targetValue.doubleValue();
    }

    @Override
    protected Float increaseValue(final Float value) {
        return 2 * (value + 1);
    }
}
