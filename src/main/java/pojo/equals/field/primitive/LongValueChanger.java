package pojo.equals.field.primitive;


class LongValueChanger extends PrimitiveValueChanger<Long> {
    @Override
    public boolean areDifferentValues(Long sourceValue, Long targetValue) {
        return sourceValue.longValue() != targetValue.longValue();
    }

    @Override
    protected Long increaseValue(Long value) {
        return value.longValue() + 1;
    }
}
