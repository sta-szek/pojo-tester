package pl.pojo.tester.internal.field.primitive;


class FloatValueChanger extends AbstractPrimitiveValueChanger<Float> {

    @Override
    protected Float increase(final Float value) {
        return 2 * (value + 1);
    }
}
