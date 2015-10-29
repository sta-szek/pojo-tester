package pojo.equals.field.primitive;

class CharacterValueChanger extends PrimitiveValueChanger<Character> {
    @Override
    public boolean areDifferentValues(Character sourceValue, Character targetValue) {
        return sourceValue.charValue() != targetValue.charValue();
    }

    @Override
    protected Character increaseValue(Character value) {
        return Character.valueOf((char) (value.charValue() + 1));
    }
}
