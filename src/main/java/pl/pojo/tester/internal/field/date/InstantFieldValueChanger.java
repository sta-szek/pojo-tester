package pl.pojo.tester.internal.field.date;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.time.Instant;

class InstantFieldValueChanger extends AbstractFieldValueChanger<Instant> {

    @Override
    protected Instant increaseValue(final Instant value, final Class<?> type) {
        return value.plusSeconds(3600);
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(Instant.class);
    }
}
