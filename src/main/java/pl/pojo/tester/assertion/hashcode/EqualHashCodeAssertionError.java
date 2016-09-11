package pl.pojo.tester.assertion.hashcode;


class EqualHashCodeAssertionError extends HashCodeAssertionError {

    private static final String CONSTRAINT_EQUAL = "The hashCode method should return same hash code for equal objects.\n"
                                                   + "Current implementation returns different values.\n"
                                                   + "Object:\n"
                                                   + "%s\n"
                                                   + "and\n"
                                                   + "%s\n"
                                                   + "have two different hash codes:\n"
                                                   + "%s\n"
                                                   + "and\n"
                                                   + "%s";
    private final Object testedObject;
    private final Object secondObject;
    private final int firstHashCode;
    private final int secondHashCode;

    EqualHashCodeAssertionError(final Class<?> testedCass,
                                final Object testedObject,
                                final Object secondObject,
                                final int firstHashCode,
                                final int secondHashCode) {
        super(testedCass);
        this.testedObject = testedObject;
        this.secondObject = secondObject;
        this.firstHashCode = firstHashCode;
        this.secondHashCode = secondHashCode;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_EQUAL, testedObject, secondObject, firstHashCode, secondHashCode);
    }
}
