package pl.pojo.tester.internal.field;

import org.apache.commons.lang3.StringUtils;

class StringValueChanger extends AbstractFieldValueChanger<String> {

    @Override
    public boolean areDifferentValues(final String sourceValue, final String targetValue) {
        return !StringUtils.equals(sourceValue, targetValue);
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(String.class);
    }

    @Override
    protected String increaseValue(final String value, final Class<?> type) {
        return value + "++increased";
    }
}
