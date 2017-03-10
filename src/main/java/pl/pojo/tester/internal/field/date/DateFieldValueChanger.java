package pl.pojo.tester.internal.field.date;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.util.Date;

class DateFieldValueChanger extends AbstractFieldValueChanger<Date> {

    @Override
    public boolean areDifferentValues(final Date sourceValue, final Date targetValue) {
        return !sourceValue.equals(targetValue);
    }

    @Override
    protected Date increaseValue(final Date value, final Class<?> type) {
        value.setTime(value.getTime() + 1000);
        return value;
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(Date.class);
    }
}





