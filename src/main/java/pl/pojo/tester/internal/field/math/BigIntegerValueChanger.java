package pl.pojo.tester.internal.field.math;

import pl.pojo.tester.internal.field.AbstractFieldValueChanger;

import java.math.BigInteger;


public class BigIntegerValueChanger extends AbstractFieldValueChanger<BigInteger> {

    @Override
    protected BigInteger increaseValue(final BigInteger value, final Class<?> type) {
        return value.add(BigInteger.ONE);
    }

    @Override
    protected boolean canChange(final Class<?> type) {
        return type.equals(BigInteger.class);
    }
}
