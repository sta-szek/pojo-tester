package pl.pojo.tester.internal.field;

import java.util.UUID;

class UUIDValueChanger extends AbstractFieldValueChanger<UUID> {

    @Override
    protected boolean canChange(final Class type) {
        return type.equals(UUID.class);
    }

    @Override
    protected UUID increaseValue(final UUID value, final Class type) {
        UUID random;
        do {
            random = UUID.randomUUID();
        } while (!areDifferentValues(value, random));
        return random;
    }
}
