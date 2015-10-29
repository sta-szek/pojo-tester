package pojo.equals.field.primitive;


class BooleanValueChanger extends PrimitiveValueChanger<Boolean> {
    @Override
    public boolean areDifferentValues(Boolean sourceValue, Boolean targetValue) {
        return sourceValue.booleanValue() != targetValue.booleanValue();
    }

    @Override
    protected Boolean increaseValue(Boolean value) {
        return Boolean.valueOf(!value.booleanValue());
    }
}
