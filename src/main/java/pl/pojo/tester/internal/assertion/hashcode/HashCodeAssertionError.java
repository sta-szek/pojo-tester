package pl.pojo.tester.internal.assertion.hashcode;


import pl.pojo.tester.internal.assertion.AssertionError;

public abstract class HashCodeAssertionError extends AssertionError {


    HashCodeAssertionError(final Class<?> testedCass) {
        super(testedCass);
    }

    @Override
    protected String getErrorPrefix() {
        return String.format("Class %s has bad 'hashCode' method implementation.", testedCass.getCanonicalName());
    }
}
