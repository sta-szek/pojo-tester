package org.pojo.tester.field;

public class ImpossibleEnumValueChange extends RuntimeException {

    public ImpossibleEnumValueChange(final Class<?> type) {
        super("Enum with type '" + type.getName() + "' has no enum constants. The only value of field with this type is null.");
    }
}
