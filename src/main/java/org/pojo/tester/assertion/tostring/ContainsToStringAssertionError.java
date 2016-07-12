package org.pojo.tester.assertion.tostring;


class ContainsToStringAssertionError extends ToStringAssertionError {

    private static final String CONSTRAINT_CONTAINS = "The toString method should contain:\n"
                                                      + "%s\n"
                                                      + "But does not.\n"
                                                      + "Result of toString:\n"
                                                      + "%s";
    private final String value;
    private final String toString;

    ContainsToStringAssertionError(final Class<?> testedCass,
                                   final String value,
                                   final String toString) {
        super(testedCass);
        this.value = value;
        this.toString = toString;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_CONTAINS, value, toString);
    }
}
