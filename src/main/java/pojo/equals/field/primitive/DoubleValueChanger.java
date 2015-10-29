package pojo.equals.field.primitive;


class DoubleValueChanger extends PrimitiveValueChanger<Double> {
    @Override
    public boolean areDifferentValues(Double sourceValue, Double targetValue) {
        return sourceValue.doubleValue() != targetValue.doubleValue();
    }

    @Override
    protected Double increaseValue(Double value) {
        return Double.valueOf(value.doubleValue() + 1);
    }
}
