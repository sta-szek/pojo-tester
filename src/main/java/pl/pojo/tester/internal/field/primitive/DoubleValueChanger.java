package pl.pojo.tester.internal.field.primitive;


class DoubleValueChanger extends AbstractPrimitiveValueChanger<Double> {

    @Override
    public boolean areDifferent(final Double sourceValue, final Double targetValue) {
        return sourceValue.doubleValue() != targetValue.doubleValue();
    }

    @Override
    public Double increaseValue(final Double value, final Class<?> type) {
        return 2 * (value + 1);
    }
}
