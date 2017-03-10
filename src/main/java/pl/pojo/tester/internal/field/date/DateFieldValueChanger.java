package pl.pojo.tester.internal.field.date;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.util.Date;

class DateFieldValueChanger extends AbstractFieldValueChanger<Date> {

    @Override
    protected Date increaseValue(final Date value, final Class<?> type) {
        return Date.from(value.toInstant().plusSeconds(100));
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(Date.class);
    }
}





