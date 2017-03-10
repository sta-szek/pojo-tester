package pl.pojo.tester.internal.field.primitive;

class IntegerValueChanger extends AbstractPrimitiveValueChanger<Integer> {

    @Override
    protected Integer increase(final Integer value) {
        return value + 1;
    }
}
