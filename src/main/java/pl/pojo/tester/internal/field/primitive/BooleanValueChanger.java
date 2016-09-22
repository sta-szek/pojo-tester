package pl.pojo.tester.internal.field.primitive;

class BooleanValueChanger extends AbstractPrimitiveValueChanger<Boolean> {

    @Override
    public boolean areDifferent(final Boolean sourceValue, final Boolean targetValue) {
        return sourceValue.booleanValue() != targetValue.booleanValue();
    }

    @Override
    protected Boolean increase(final Boolean value) {
        return !value;
    }
}
