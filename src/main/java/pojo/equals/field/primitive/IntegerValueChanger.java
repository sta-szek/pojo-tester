package pojo.equals.field.primitive;

class IntegerValueChanger extends PrimitiveValueChanger<Integer> {
    @Override
    public boolean areDifferentValues(Integer sourceValue, Integer targetValue) {
        return sourceValue.intValue() != targetValue.intValue();
    }

    @Override
    protected Integer increaseValue(Integer value) {
        return Integer.valueOf(value.intValue() + 1);
    }
}
