package pl.pojo.tester.internal.assertion.setter;


import java.lang.reflect.Field;
import pl.pojo.tester.internal.assertion.AssertionError;

class SetterAssertionError extends AssertionError {

    private static final String CONSTRAINT_SETTER = "The setter method for field '%s' should set field value.\n"
                                                    + "Current implementation does not set the value.\n"
                                                    + "Expected value:\n"
                                                    + "%s\n"
                                                    + "but was:\n"
                                                    + "%s";

    private final Field field;
    private final Object expectedValue;
    private final Object currentValue;

    SetterAssertionError(final Class<?> testedCass, final Field field, final Object expectedValue, final Object currentValue) {
        super(testedCass);
        this.field = field;
        this.expectedValue = expectedValue;
        this.currentValue = currentValue;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_SETTER, field, expectedValue, currentValue);
    }

    @Override
    protected String getErrorPrefix() {
        return String.format("Class %s has bad 'setter' method implementation.", testedCass.getCanonicalName());
    }

}
