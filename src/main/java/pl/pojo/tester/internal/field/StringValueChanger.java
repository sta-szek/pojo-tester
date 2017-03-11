package pl.pojo.tester.internal.field;

class StringValueChanger extends AbstractFieldValueChanger<String> {

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(String.class);
    }

    @Override
    protected String increaseValue(final String value, final Class<?> type) {
        return value + "++increased";
    }
}
