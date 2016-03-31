package pojo.equals.field.primitive;

class BooleanValueChanger extends PrimitiveValueChanger<Boolean> {

    @Override
    public boolean areDifferentValues(final Boolean sourceValue, final Boolean targetValue) {
        return sourceValue.booleanValue() != targetValue.booleanValue();
    }

    @Override
    protected Boolean increaseValue(final Boolean value) {
        return !value;
    }
}
