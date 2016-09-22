package pl.pojo.tester.internal.assertion.tostring;


class NotContainToStringAssertionError extends ToStringAssertionError {

    private static final String CONSTRAINT_NOT_CONTAIN = "The toString method should not contain:\n"
                                                         + "%s\n"
                                                         + "but does.\n"
                                                         + "Result of toString:\n"
                                                         + "%s";
    private final String value;
    private final String toString;

    NotContainToStringAssertionError(final Class<?> testedCass,
                                     final String value,
                                     final String toString) {
        super(testedCass);
        this.value = value;
        this.toString = toString;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_NOT_CONTAIN, value, toString);
    }
}
