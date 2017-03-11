package pl.pojo.tester.internal.field.primitive;


class LongValueChanger extends AbstractPrimitiveValueChanger<Long> {

    @Override
    protected Long increase(final Long value) {
        return value + 1;
    }
}
