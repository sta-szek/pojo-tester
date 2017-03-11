package pl.pojo.tester.internal.field.primitive;

class CharacterValueChanger extends AbstractPrimitiveValueChanger<Character> {

    @Override
    protected Character increase(final Character value) {
        return (char) (value + 1);
    }
}
