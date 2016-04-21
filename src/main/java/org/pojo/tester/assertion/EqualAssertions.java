package org.pojo.tester.assertion;


public class EqualAssertions {

    private static final String CONSTRAINT_REFLEXIVE = "Class %s has bad 'equals' method implementation."
                                                       + "The equals method should return true if object is comparing to itself."
                                                       + "Current implementation returns false.\n"
                                                       + "Object:\n"
                                                       + "%s\n"
                                                       + "should be equal to:\n"
                                                       + "%s\n";
    private static final String CONSTRAINT_CONSISTENT = "Class %s has bad 'equals' method implementation."
                                                        + "The equals method should be consistent when comparing same objects multiple "
                                                        + "times.\n"
                                                        + "Current implementation returns different results.\n"
                                                        + "When comparing object:\n"
                                                        + "%s\n"
                                                        + "First result was: %s and second time was: %s\n.\n";
    private static final String CONSTRAINT_SYMMETRIC = "Class %s has bad 'equals' method implementation.\n"
                                                       + "The equals method should return true for both a.equals(b) and b.equals(a).\n"
                                                       + "Current implementation returns:\n"
                                                       + "%s for a.equals(b),\n"
                                                       + "%s for b.equals(a),\n"
                                                       + "where a is:\n"
                                                       + "%s\n"
                                                       + "b is:\n"
                                                       + "%s\n";
    private static final String CONSTRAINT_TRANSITIVE = "Class %s has bad 'equals' method implementation.\n"
                                                        + "The equals method should return true in all cases: a.equals(b), b.equals(c) and "
                                                        + "a.equals(c).\n"
                                                        + "Current implementation returns:\n"
                                                        + "%s for a.equals(b),\n"
                                                        + "%s for b.equals(c),\n"
                                                        + "%s for a.equals(c),\n"
                                                        + "where a is:\n"
                                                        + "%s\n"
                                                        + "b is:\n"
                                                        + "%s\n"
                                                        + "c is:\n"
                                                        + "%s\n";
    private static final String CONSTRAINT_NULL = "Class %s has bad 'equals' method implementation.\n"
                                                  + "The equals method should return false if object is comparing to null.\n"
                                                  + "Current implementation returns true.\n";
    private static final String CONSTRAINT_OTHER_TYPE = "Class %s has bad 'equals' method implementation.\n"
                                                        + "The equals method should return false if object is comparing to object with "
                                                        + "different type.\n"
                                                        + "Current implementation returns true.\n"
                                                        + "Object:\n"
                                                        + "%s\n"
                                                        + "should not be equal to:\n"
                                                        + "%s\n";
    private static final String CONSTRAINT_NOT_EQUAL = "Class %s has bad 'equals' method implementation.\n"
                                                       + "The equals method should return false if objects should not be equal.\n"
                                                       + "Current implementation returns true.\n"
                                                       + "Object:\n"
                                                       + "%s\n"
                                                       + "should not be equal to:\n"
                                                       + "%s\n";

    private final ResultBuilder resultBuilder;
    private final Object objectUnderAssert;
    private final Class<?> classUnderTest;

    EqualAssertions(final ResultBuilder resultBuilder, final Object objectUnderAssert) {
        this.resultBuilder = resultBuilder;
        this.objectUnderAssert = objectUnderAssert;
        this.classUnderTest = objectUnderAssert.getClass();
    }

    public void isReflexive() {
        final boolean result = objectUnderAssert.equals(objectUnderAssert);
        final String message = formatMessage(CONSTRAINT_REFLEXIVE, classUnderTest.getCanonicalName(), objectUnderAssert, objectUnderAssert);
        appendResult(result, "isReflexive", message);
    }

    public void isConsistent() {
        final boolean result1 = objectUnderAssert.equals(objectUnderAssert);
        final boolean result2 = objectUnderAssert.equals(objectUnderAssert);
        final boolean result = result1 == result2;
        final String message = formatMessage(CONSTRAINT_CONSISTENT,
                                             classUnderTest.getCanonicalName(),
                                             objectUnderAssert,
                                             result1,
                                             result2);
        appendResult(result, "isConsistent", message);
    }

    public void isSymmetric(final Object otherObject) {
        final boolean result1 = objectUnderAssert.equals(otherObject);
        final boolean result2 = otherObject.equals(objectUnderAssert);
        final boolean result = result1 == result2;
        final String message = formatMessage(CONSTRAINT_SYMMETRIC,
                                             classUnderTest.getCanonicalName(),
                                             result1,
                                             result2,
                                             objectUnderAssert,
                                             otherObject);
        appendResult(result, "isSymmetric", message);
    }

    public void isTransitive(final Object b, final Object c) {
        final boolean result1 = objectUnderAssert.equals(b);
        final boolean result2 = b.equals(c);
        final boolean result3 = objectUnderAssert.equals(c);
        final boolean partialResult1 = result1 == result2;
        final boolean partialResult2 = result2 == result3;
        final boolean result = partialResult1 && partialResult2;
        final String message = formatMessage(CONSTRAINT_TRANSITIVE,
                                             classUnderTest.getCanonicalName(),
                                             result1,
                                             result2,
                                             result3,
                                             objectUnderAssert,
                                             b,
                                             c);
        appendResult(result, "isTransitive", message);
    }

    public void isNotEqualToNull() {
        final boolean result = !objectUnderAssert.equals(null);
        final String message = formatMessage(CONSTRAINT_NULL, classUnderTest);
        appendResult(result, "isNotEqualToNull", message);
    }

    public void isNotEqualToObjectWithDifferentType(final Object otherObject) {
        final boolean result = !objectUnderAssert.equals(otherObject);
        final String message = formatMessage(CONSTRAINT_OTHER_TYPE, classUnderTest, objectUnderAssert, otherObject);
        appendResult(result, "isNotEqualToObjectWithDifferentType", message);
    }

    public void isNotEqualTo(final Object objectToCompare) {
        final boolean result = !objectUnderAssert.equals(objectToCompare);
        final String message = formatMessage(CONSTRAINT_NOT_EQUAL, classUnderTest, objectUnderAssert, objectToCompare);
        appendResult(result, "isNotEqualTo", message);
    }

    private String formatMessage(final String message, final Object... objects) {
        return String.format(message, objects);
    }

    private void appendResult(final boolean pass, final String testName, final String errorMessage) {
        if (pass) {
            appendPass(classUnderTest, testName);
        } else {
            appendFail(classUnderTest, testName, errorMessage);
        }
    }

    private void appendFail(final Class<?> testedClass, final String testName, final String errorMessage) {
        resultBuilder.fail(testedClass, testName, errorMessage);
    }

    private void appendPass(final Class<?> testedClass, final String testName) {
        resultBuilder.pass(testedClass, testName);
    }
}
