package org.pojo.tester.assertion.equals;


import org.pojo.tester.assertion.AssertionError;

abstract class EqualsAssertionError extends AssertionError {


    EqualsAssertionError(final Class<?> testedCass) {
        super(testedCass);
    }

    @Override
    protected String getErrorPrefix() {
        return String.format("Class %s has bad 'equals' method implementation.", testedCass.getCanonicalName());
    }
}
