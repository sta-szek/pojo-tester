package pl.pojo.tester.internal.field;

class ImpossibleEnumValueChangeException extends RuntimeException {

    public ImpossibleEnumValueChangeException(final Class<?> type) {
        super("Enum with type '" + type.getName() + "' has no enum constants. The only value of field with this type is null.");
    }
}
