package pl.pojo.tester.internal.field.primitive;


class FloatValueChanger extends AbstractPrimitiveValueChanger<Float> {

    @Override
    public boolean areDifferent(final Float sourceValue, final Float targetValue) {
        return sourceValue.doubleValue() != targetValue.doubleValue();
    }

    @Override
    public Float increaseValue(final Float value, final Class<?> type) {
        return 2 * (value + 1);
    }
}
