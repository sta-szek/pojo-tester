package pl.pojo.tester.internal.field.primitive;

class BooleanValueChanger extends AbstractPrimitiveValueChanger<Boolean> {

    @Override
    protected Boolean increase(final Boolean value) {
        return !value;
    }
}
