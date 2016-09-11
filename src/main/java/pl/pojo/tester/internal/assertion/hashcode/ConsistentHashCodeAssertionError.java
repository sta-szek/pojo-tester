package pl.pojo.tester.internal.assertion.hashcode;


class ConsistentHashCodeAssertionError extends HashCodeAssertionError {

    private static final String CONSTRAINT_CONSISTENT = "The hashCode method should return same hash code for same object.\n"
                                                        + "Current implementation returns different values.\n"
                                                        + "Object:\n"
                                                        + "%s\n"
                                                        + "has two different hash codes:\n"
                                                        + "%s\n"
                                                        + "and"
                                                        + "\n%s";
    private final Object testedObject;
    private final int firstHashCode;
    private final int secondHashCode;

    ConsistentHashCodeAssertionError(final Class<?> testedCass,
                                     final Object testedObject,
                                     final int firstHashCode,
                                     final int secondHashCode) {
        super(testedCass);
        this.testedObject = testedObject;
        this.firstHashCode = firstHashCode;
        this.secondHashCode = secondHashCode;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_CONSISTENT, testedObject, firstHashCode, secondHashCode);
    }
}
