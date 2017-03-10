package pl.pojo.tester.internal.field.primitive;


class DoubleValueChanger extends AbstractPrimitiveValueChanger<Double> {

    @Override
    protected Double increase(final Double value) {
        return 2 * (value + 1);
    }
}
