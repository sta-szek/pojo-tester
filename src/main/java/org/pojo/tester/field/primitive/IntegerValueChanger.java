package org.pojo.tester.field.primitive;

class IntegerValueChanger extends PrimitiveValueChanger<Integer> {
    @Override
    public boolean areDifferentValues(final Integer sourceValue, final Integer targetValue) {
        return sourceValue.intValue() != targetValue.intValue();
    }

    @Override
    protected Integer increaseValue(final Integer value) {
        return value + 1;
    }
}
