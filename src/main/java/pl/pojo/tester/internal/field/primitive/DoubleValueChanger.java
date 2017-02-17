package pl.pojo.tester.internal.field.primitive;


class DoubleValueChanger extends AbstractPrimitiveValueChanger<Double> {

    @Override
    public boolean areDifferent(final Double sourceValue, final Double targetValue) {
        return Double.compare(sourceValue, targetValue) != 0;
    }

    @Override
    protected Double increase(final Double value) {
        return 2 * (value + 1);
    }
}
