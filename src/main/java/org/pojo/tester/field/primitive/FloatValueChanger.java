package org.pojo.tester.field.primitive;


class FloatValueChanger extends AbstractPrimitiveValueChanger<Float> {

    @Override
    public boolean areDifferent(final Float sourceValue, final Float targetValue) {
        return sourceValue.doubleValue() != targetValue.doubleValue();
    }

    @Override
    protected Float increaseValue(final Float value) {
        return 2 * (value + 1);
    }
}
