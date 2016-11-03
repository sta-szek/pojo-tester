package pl.pojo.tester.internal.assertion.equals;


import pl.pojo.tester.internal.assertion.AssertionError;

public abstract class AbstractEqualsAssertionError extends AssertionError {


    AbstractEqualsAssertionError(final Class<?> testedCass) {
        super(testedCass);
    }

    @Override
    protected String getErrorPrefix() {
        return String.format("Class %s has bad 'equals' method implementation.", testedCass.getCanonicalName());
    }
}
