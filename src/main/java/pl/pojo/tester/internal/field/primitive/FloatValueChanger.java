package pl.pojo.tester.internal.field.primitive;


class FloatValueChanger extends AbstractPrimitiveValueChanger<Float> {

    @Override
    public boolean areDifferent(final Float sourceValue, final Float targetValue) {
        return Float.compare(sourceValue, targetValue) != 0;
    }

    @Override
    protected Float increase(final Float value) {
        return 2 * (value + 1);
    }
}
