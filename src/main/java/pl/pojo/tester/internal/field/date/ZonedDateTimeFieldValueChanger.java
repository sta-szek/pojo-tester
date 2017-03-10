package pl.pojo.tester.internal.field.date;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.time.ZonedDateTime;

class ZonedDateTimeFieldValueChanger extends AbstractFieldValueChanger<ZonedDateTime> {
    
    @Override
    protected ZonedDateTime increaseValue(final ZonedDateTime value, final Class<?> type) {
        return value.plusDays(1);
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(ZonedDateTime.class);
    }
}