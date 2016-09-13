package pl.pojo.tester.internal.field.primitive;

class CharacterValueChanger extends AbstractPrimitiveValueChanger<Character> {

    @Override
    public boolean areDifferent(final Character sourceValue, final Character targetValue) {
        return sourceValue.charValue() != targetValue.charValue();
    }

    @Override
    public Character increaseValue(final Character value, final Class<?> type) {
        return (char) (value + 1);
    }
}
