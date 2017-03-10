package pl.pojo.tester.internal.field.date;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.time.ZonedDateTime;

class ZonedDateTimeFieldValueChanger extends AbstractFieldValueChanger<ZonedDateTime> {

    @Override
    public boolean areDifferentValues(final ZonedDateTime sourceValue, final ZonedDateTime targetValue) {
        return !sourceValue.equals(targetValue);
    }

    @Override
    protected ZonedDateTime increaseValue(final ZonedDateTime value, final Class<?> type) {
        return value.plusDays(1);
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(ZonedDateTime.class);
    }
}