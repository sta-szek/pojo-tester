package pl.pojo.tester.internal.field.primitive;


class LongValueChanger extends AbstractPrimitiveValueChanger<Long> {

    @Override
    public boolean areDifferent(final Long sourceValue, final Long targetValue) {
        return sourceValue.longValue() != targetValue.longValue();
    }

    @Override
    protected Long increase(final Long value) {
        return value + 1;
    }
}
