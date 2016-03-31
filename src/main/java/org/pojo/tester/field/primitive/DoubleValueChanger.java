package org.pojo.tester.field.primitive;


class DoubleValueChanger extends PrimitiveValueChanger<Double> {

    @Override
    public boolean areDifferentValues(final Double sourceValue, final Double targetValue) {
        return sourceValue.doubleValue() != targetValue.doubleValue();
    }

    @Override
    protected Double increaseValue(final Double value) {
        return 2 * (value + 1);
    }
}
