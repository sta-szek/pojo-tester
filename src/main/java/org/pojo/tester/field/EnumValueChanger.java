package org.pojo.tester.field;


import java.lang.reflect.Field;

class EnumValueChanger extends AbstractFieldValueChanger<Enum> {


    @Override
    public boolean areDifferentValues(final Enum sourceValue, final Enum targetValue) {
        return sourceValue != targetValue;
    }

    @Override
    protected boolean canChange(final Field field) {
        return field.getType()
                    .isEnum();
    }

    @Override
    protected Enum increaseValue(final Enum value, final Class<?> type) {
        if (value == null) {
            return firstValue(type);
        } else {
            return findDifferentValue(value, type);
        }
    }

    private Enum findDifferentValue(final Enum value, final Class<?> type) {
        final Enum[] enumConstants = (Enum[]) type.getEnumConstants();
        for (final Enum enumConstant : enumConstants) {
            if (areDifferentValues(value, enumConstant)) {
                return enumConstant;
            }
        }
        return null;
    }

    private Enum firstValue(final Class<?> type) {
        final Object[] enumConstants = type.getEnumConstants();
        if (enumConstants.length == 0) {
            throw new ImpossibleEnumValueChangeException(type);
        }
        return (Enum) enumConstants[0];
    }
}
