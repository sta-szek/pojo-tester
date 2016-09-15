package pl.pojo.tester.internal.field.primitive;

class IntegerValueChanger extends AbstractPrimitiveValueChanger<Integer> {

    @Override
    public boolean areDifferent(final Integer sourceValue, final Integer targetValue) {
        return sourceValue.intValue() != targetValue.intValue();
    }

    @Override
    protected Integer increase(final Integer value) {
        return value + 1;
    }
}
