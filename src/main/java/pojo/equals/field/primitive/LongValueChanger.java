package pojo.equals.field.primitive;


class LongValueChanger extends PrimitiveValueChanger<Long> {

    @Override
    public boolean areDifferentValues(final Long sourceValue, final Long targetValue) {
        return sourceValue.longValue() != targetValue.longValue();
    }

    @Override
    protected Long increaseValue(final Long value) {
        return value + 1;
    }
}
