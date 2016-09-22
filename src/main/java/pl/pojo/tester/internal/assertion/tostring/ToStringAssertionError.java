package pl.pojo.tester.internal.assertion.tostring;


import pl.pojo.tester.internal.assertion.AssertionError;

abstract class ToStringAssertionError extends AssertionError {

    ToStringAssertionError(final Class<?> testedCass) {
        super(testedCass);
    }

    @Override
    protected String getErrorPrefix() {
        return String.format("Class %s has bad 'toString' method implementation.", testedCass.getCanonicalName());
    }
}
