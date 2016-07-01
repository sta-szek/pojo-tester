package org.pojo.tester.assertion.tostring;


import org.pojo.tester.assertion.AssertionError;

public abstract class ToStringAssertionError extends AssertionError {


    ToStringAssertionError(final Class<?> testedCass) {
        super(testedCass);
    }

    @Override
    protected String getErrorPrefix() {
        return String.format("Class %s has bad 'toString' method implementation.", testedCass.getCanonicalName());
    }
}
