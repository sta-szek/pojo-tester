package org.pojo.tester.field.primitive;

class BooleanValueChanger extends AbstractPrimitiveValueChanger<Boolean> {

    @Override
    public boolean areDifferent(final Boolean sourceValue, final Boolean targetValue) {
        return sourceValue.booleanValue() != targetValue.booleanValue();
    }

    @Override
    protected Boolean increaseValue(final Boolean value) {
        return !value;
    }
}
