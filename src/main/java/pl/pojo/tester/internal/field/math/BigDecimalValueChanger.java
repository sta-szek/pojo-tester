package pl.pojo.tester.internal.field.math;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.math.BigDecimal;


public class BigDecimalValueChanger extends AbstractFieldValueChanger<BigDecimal> {

    @Override
    protected BigDecimal increaseValue(final BigDecimal value, final Class<?> type) {
        return value.add(BigDecimal.ONE);
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(BigDecimal.class);
    }
}
