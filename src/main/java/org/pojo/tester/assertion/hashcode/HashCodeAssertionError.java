package org.pojo.tester.assertion.hashcode;


import org.pojo.tester.assertion.AssertionError;

public abstract class HashCodeAssertionError extends AssertionError {


    HashCodeAssertionError(final Class<?> testedCass) {
        super(testedCass);
    }

    @Override
    protected String getErrorPrefix() {
        return String.format("Class %s has bad 'hashCode' method implementation.", testedCass.getCanonicalName());
    }
}
