package org.pojo.tester.assertion.equals;


public class SymmetricEqualsAssertionError extends EqualsAssertionError {

    private static final String CONSTRAINT_SYMMETRIC = "The equals method should return true for both a.equals(b) and b.equals(a).\n"
                                                       + "Current implementation returns:\n"
                                                       + "%s for a.equals(b),\n"
                                                       + "%s for b.equals(a),\n"
                                                       + "where a is:\n"
                                                       + "%s\n"
                                                       + "b is:\n"
                                                       + "%s\n";
    private final Object testedObject;
    private final Object otherObject;
    private final boolean firstResult;
    private final boolean secondResult;


    SymmetricEqualsAssertionError(final Class<?> testedCass,
                                  final Object testedObject,
                                  final Object otherObject,
                                  final boolean firstResult,
                                  final boolean secondResult) {
        super(testedCass);
        this.testedObject = testedObject;
        this.otherObject = otherObject;
        this.firstResult = firstResult;
        this.secondResult = secondResult;
    }

    @Override
    protected String getDetailedMessage() {
        return String.format(CONSTRAINT_SYMMETRIC, firstResult, secondResult, testedObject, otherObject);
    }
}
