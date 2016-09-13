package pl.pojo.tester.internal.field.primitive;

class IntegerValueChanger extends AbstractPrimitiveValueChanger<Integer> {

    @Override
    public boolean areDifferent(final Integer sourceValue, final Integer targetValue) {
        return sourceValue.intValue() != targetValue.intValue();
    }

    @Override
    public Integer increaseValue(final Integer value, final Class<?> type) {
        return value + 1;
    }
}
