package pl.pojo.tester.internal.field.date;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.time.LocalDateTime;

class LocalDateTimeFieldValueChanger extends AbstractFieldValueChanger<LocalDateTime> {

    @Override
    public boolean areDifferentValues(final LocalDateTime sourceValue, final LocalDateTime targetValue) {
        return !sourceValue.equals(targetValue);
    }

    @Override
    protected LocalDateTime increaseValue(final LocalDateTime value, final Class<?> type) {
        return value.plusDays(1);
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(LocalDateTime.class);
    }
}