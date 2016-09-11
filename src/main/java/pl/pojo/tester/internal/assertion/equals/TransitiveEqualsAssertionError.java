package pl.pojo.tester.internal.assertion.equals;


class TransitiveEqualsAssertionError extends EqualsAssertionError {

    private static final String CONSTRAINT_TRANSITIVE = "The equals method should return true in all cases: a.equals(b), b.equals(c) and "
                                                        + "a.equals(c).\n"
                                                        + "Current implementation returns:\n"
                                                        + "%s for a.equals(b),\n"
                                                        + "%s for b.equals(c),\n"
                                                        + "%s for a.equals(c),\n"
                                                        + "where 'a' is:\n"
                                                        + "%s\n"
                                                        + "and 'b' is:\n"
                                                        + "%s\n"
                                                        + "and 'c' is:\n"
                                                        + "%s";
    private final Object a;
    private final Object b;
    private final Object c;
    private final boolean firstResult;
    private final boolean secondResult;
    private final boolean thirdResult;


    TransitiveEqualsAssertionError(final Class<?> testedCass,
                                   final Object a,
                                   final Object b,
                                   final Object c,
                                   final boolean firstResult,
                                   final boolean secondResult,
                                   final boolean thirdResult) {
        super(testedCass);
        this.a = a;
        this.b = b;
        this.c = c;
        this.firstResult = firstResult;
        this.secondResult = secondResult;
        this.thirdResult = thirdResult;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_TRANSITIVE, firstResult, secondResult, thirdResult, a, b, c);
    }
}
