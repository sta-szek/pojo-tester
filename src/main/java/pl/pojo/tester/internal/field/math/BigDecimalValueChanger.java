package pl.pojo.tester.internal.field.math;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.math.BigDecimal;


public class BigDecimalValueChanger extends AbstractFieldValueChanger<BigDecimal> {

    @Override
    public boolean areDifferentValues(final BigDecimal sourceValue, final BigDecimal targetValue) {
        if (sourceValue == targetValue) {
            return false;
        }
        if (sourceValue == null || targetValue == null) {
            return true;
        }
        return !sourceValue.equals(targetValue);
    }

    @Override
    protected BigDecimal increaseValue(final BigDecimal value, final Class<?> type) {
        return value.add(BigDecimal.ONE);
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(BigDecimal.class);
    }
}
