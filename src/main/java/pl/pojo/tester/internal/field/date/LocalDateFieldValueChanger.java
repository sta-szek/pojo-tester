package pl.pojo.tester.internal.field.date;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.time.LocalDate;

class LocalDateFieldValueChanger extends AbstractFieldValueChanger<LocalDate> {

    @Override
    protected LocalDate increaseValue(final LocalDate value, final Class<?> type) {
        return value.plusDays(1);
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(LocalDate.class);
    }
}
