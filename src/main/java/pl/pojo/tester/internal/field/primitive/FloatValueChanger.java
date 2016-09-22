package pl.pojo.tester.internal.field.primitive;


class FloatValueChanger extends AbstractPrimitiveValueChanger<Float> {

    @Override
    public boolean areDifferent(final Float sourceValue, final Float targetValue) {
        return sourceValue.doubleValue() != targetValue.doubleValue();
    }

    @Override
    protected Float increase(final Float value) {
        return 2 * (value + 1);
    }
}
