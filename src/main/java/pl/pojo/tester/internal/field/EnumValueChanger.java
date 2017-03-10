package pl.pojo.tester.internal.field;


class EnumValueChanger extends AbstractFieldValueChanger<Enum> {

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.isEnum();
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
