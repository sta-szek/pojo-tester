package pl.pojo.tester.internal.assertion.equals;


class ConsistentEqualsAssertionError extends AbstractEqualsAssertionError {

    private static final String CONSTRAINT_CONSISTENT = "The equals method should be consistent when comparing same objects multiple "
                                                        + "times.\n"
                                                        + "Current implementation returns different results.\n"
                                                        + "When comparing object:\n"
                                                        + "%s\n"
                                                        + "to itself, first result was '%s' and second time was '%s'.";
    private final Object testedObject;
    private final boolean firstResult;
    private final boolean secondResult;


    ConsistentEqualsAssertionError(final Class<?> testedCass,
                                   final Object testedObject,
                                   final boolean firstResult,
                                   final boolean secondResult) {
        super(testedCass);
        this.testedObject = testedObject;
        this.firstResult = firstResult;
        this.secondResult = secondResult;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_CONSISTENT, testedObject, firstResult, secondResult);
    }
}
