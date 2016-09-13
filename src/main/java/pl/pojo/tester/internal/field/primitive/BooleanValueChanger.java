package pl.pojo.tester.internal.field.primitive;

class BooleanValueChanger extends AbstractPrimitiveValueChanger<Boolean> {

    @Override
    public boolean areDifferent(final Boolean sourceValue, final Boolean targetValue) {
        return sourceValue.booleanValue() != targetValue.booleanValue();
    }

    @Override
    public Boolean increaseValue(final Boolean value, final Class<?> type) {
        return !value;
    }
}
