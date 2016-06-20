package org.pojo.tester.field.primitive;

class CharacterValueChanger extends AbstractPrimitiveValueChanger<Character> {

    @Override
    public boolean areDifferent(final Character sourceValue, final Character targetValue) {
        return sourceValue.charValue() != targetValue.charValue();
    }

    @Override
    protected Character increaseValue(final Character value) {
        return (char) (value + 1);
    }
}
