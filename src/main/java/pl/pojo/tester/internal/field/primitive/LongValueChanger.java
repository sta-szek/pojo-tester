package pl.pojo.tester.internal.field.primitive;


class LongValueChanger extends AbstractPrimitiveValueChanger<Long> {

    @Override
    public boolean areDifferent(final Long sourceValue, final Long targetValue) {
        return sourceValue.longValue() != targetValue.longValue();
    }

    @Override
    public Long increaseValue(final Long value, final Class<?> type) {
        return value + 1;
    }
}
