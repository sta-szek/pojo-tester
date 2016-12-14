package pl.pojo.tester.internal.field;

import java.util.UUID;

class UUIDValueChanger extends AbstractFieldValueChanger<UUID> {

    @Override
    public boolean areDifferentValues(UUID sourceValue, UUID targetValue) {
        if (sourceValue == targetValue) {
            return false;
        }
        if (sourceValue == null || targetValue == null) {
            return true;
        }
        return !sourceValue.equals(targetValue);
    }

    @Override
    protected boolean canChange(Class type) {
        return type.equals(UUID.class);
    }

    @Override
    protected UUID increaseValue(UUID value, Class type) {
        UUID random;
        do {
            random = UUID.randomUUID();
        } while (!areDifferentValues(value, random));
        return random;
    }
}