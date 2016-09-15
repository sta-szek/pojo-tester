package pl.pojo.tester.internal.field.primitive;


class DoubleValueChanger extends AbstractPrimitiveValueChanger<Double> {

    @Override
    public boolean areDifferent(final Double sourceValue, final Double targetValue) {
        return sourceValue.doubleValue() != targetValue.doubleValue();
    }

    @Override
    protected Double increase(final Double value) {
        return 2 * (value + 1);
    }
}
